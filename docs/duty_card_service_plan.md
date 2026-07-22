# Simple Duty & Card Generation Service Plan

This document outlines the architecture, workflow, and implementation strategy for the **Ahun Duty & Card Service**. The goal is to provide a clean, simple, and developer-friendly REST API following **Hexagonal Architecture** and **HATEOAS best practices**.

---

## 1. Core Service Philosophy & Simplicity

The service is designed around a straightforward, non-redundant lifecycle:
```
┌───────────────────────────────────────────────────────────┐
│ 1. Duty Management                                        │
│    • POST   /duty       (Register duty)                   │
│    • GET    /duty       (List duties)                     │
│    • GET    /duty/{id}  (Get duty details with links)     │
│    • PUT    /duty/{id}  (Update duty)                     │
└─────────────────────────────┬─────────────────────────────┘
                              │
                              ▼
┌───────────────────────────────────────────────────────────┐
│ 2. Card Generation & Export                               │
│    • GET    /cards/render/{dutyId}   (Download PNG Card)  │
│    • GET    /cards/preview/{dutyId}  (Preview HTML Card)  │
└───────────────────────────────────────────────────────────┘
```

### Key Principles
1. **Zero Redundancy**: Fields that can be derived from `date` (such as `year` and academic `period`) are automatically computed on the backend. Callers do not need to supply them.
2. **Unified REST Endpoints**: Single handler methods accept resource identifiers via path variable (`/resource/{id}`) or query parameter (`/resource?id=...`).
3. **Hypermedia Self-Discovery (HATEOAS)**: Registering or querying a duty returns direct `_links.card-render.href` and `_links.card-preview.href` URLs, so external clients (web apps or bots) do not need to construct URL patterns manually.

---

## 2. Automatic Field Calculation Rules

When creating or updating a `Duty`:
- **Year (`year`)**: Extracted directly from `date.year`.
- **Academic Semester (`period`)**: Derived from `date.monthValue`:
  - `Months 1 to 6` (January – June) $\rightarrow$ `FIRST_SEMESTER`
  - `Months 7 to 12` (July – December) $\rightarrow$ `SECOND_SEMESTER`
- **Subtitle Text**: Formatted in Portuguese using `DateTimeFormatter.ofPattern("EEEE | d 'DE' MMMM", Locale("pt", "BR"))` appended with the earliest card-visible event start time (e.g., `SÁBADO | 20 DE MAIO - 18H`).

---

## 3. Hexagonal Architecture & Package Layout

```
com.petrolal.ahun.ahundutyservice
├── domain                         <-- Domain Entities, Enums, Exceptions & DTOs
│   ├── Duty.kt
│   ├── DutyEvent.kt
│   ├── DutyTypeEnum.kt
│   ├── SemesterEnum.kt
│   ├── Theme.kt
│   └── dto
│       ├── DutyRequestDto.kt
│       ├── DutyEventRequestDto.kt
│       └── ThemeRequestDto.kt
├── application
│   ├── ports                      <-- Inbound & Outbound Interfaces
│   │   ├── CardRenderPort.kt      (Outbound)
│   │   ├── CardUsecasePort.kt     (Inbound)
│   │   ├── DutyEventRepositoryPort.kt
│   │   ├── DutyRepositoryPort.kt
│   │   └── ThemeRepositoryPort.kt
│   └── usecases                   <-- Application Services / Business Logic
│       ├── CardUsecase.kt
│       ├── DutyEventUsecase.kt
│       ├── DutyUsecase.kt
│       └── ThemeUsecase.kt
└── infrastructure
    ├── adapters
    │   ├── inbound/rest           <-- REST Controllers & HATEOAS Assemblers
    │   │   ├── CardResource.kt
    │   │   ├── DutyEventResource.kt
    │   │   ├── DutyResource.kt
    │   │   ├── ThemeResource.kt
    │   │   └── assembler
    │   │       └── DutyModelAssembler.kt
    │   └── outbound/rendering     <-- Thymeleaf & FlyingSaucer Card Renderer
    │       └── ThymeleafCardRendererAdapter.kt
    └── persistence                <-- Database Repositories & JPA Entities
        ├── entity
        └── repository
```

---

## 4. Endpoint Specifications

### A. Duty Management (`/duty`)

| HTTP Method | Path | Description | Response Type |
|---|---|---|---|
| `POST` | `/duty` | Register a new duty | `201 Created` (`EntityModel<Duty>`) |
| `GET` | `/duty` | List duties (optional `theme`, `dutyType` filters) | `CollectionModel<EntityModel<Duty>>` |
| `GET` | `/duty/{id}` | Get duty details by UUID | `EntityModel<Duty>` |
| `PUT` | `/duty/{id}` or `/duty?id=...` | Update existing duty | `EntityModel<Duty>` |

#### Payload (`POST /duty`)
```json
{
  "themeId": "189951bc-a581-4788-a635-2642add90358",
  "dutyType": "OPENED_GIRA",
  "date": "2026-05-20",
  "description": "Gira Aberta de Exu e Cura",
  "eventIds": ["d4dd41cd-9354-4788-b3c7-8f7fee416fd4"]
}
```

#### HATEOAS HAL Response (`GET /duty/{id}` or `POST /duty`)
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "date": "2026-05-20",
  "dutyType": "OPENED_GIRA",
  "period": "FIRST_SEMESTER",
  "year": 2026,
  "description": "Gira Aberta de Exu e Cura",
  "theme": { "id": "189951bc-a581-4788-a635-2642add90358", "name": "Gira de Exu e Cura" },
  "events": [...],
  "_links": {
    "self": { "href": "http://localhost:8080/duty/3fa85f64-5717-4562-b3fc-2c963f66afa6" },
    "card-render": { "href": "http://localhost:8080/cards/render/3fa85f64-5717-4562-b3fc-2c963f66afa6", "type": "image/png" },
    "card-preview": { "href": "http://localhost:8080/cards/preview/3fa85f64-5717-4562-b3fc-2c963f66afa6", "type": "text/html" },
    "all-duties": { "href": "http://localhost:8080/duty" }
  }
}
```

### B. Card Generation (`/cards`)

| HTTP Method | Path | Description | Response Type |
|---|---|---|---|
| `GET` | `/cards/render/{dutyId}` or `/cards/render?dutyId=...` | Render PNG card for specified duty | `image/png` |
| `GET` | `/cards/preview/{dutyId}` or `/cards/preview?dutyId=...` | Preview HTML template for specified duty | `text/html` |
| `GET` | `/cards/render` | Default: Render actual month's `GIRA_ABERTA` PNG | `image/png` |
| `GET` | `/cards/preview` | Default: Preview actual month's `GIRA_ABERTA` HTML | `text/html` |

### C. Template Management & PNG Image Upload (`/templates`)

| HTTP Method | Path | Description | Content Type / Body |
|---|---|---|---|
| `GET` | `/templates` | List all templates (optional `themeId` filter) | `application/json` |
| `GET` | `/templates/{id}` | Get template by UUID | `application/json` |
| `POST` | `/templates` | Upload new PNG image template & register in DB | `multipart/form-data` (`name`, `themeId`, `file`) |
| `PUT` | `/templates/{id}` | Update existing template and/or background PNG file | `multipart/form-data` (`name`, `themeId`, `file`) |
| `DELETE` | `/templates/{id}` | Delete template record and custom image file | `204 No Content` |

---

## 5. Implementation Checklist & Verification

- [x] **Hexagonal Core & Rendering Engine**:
  - `CardUsecasePort` (inbound port), `CardRenderPort` (outbound port), `ThymeleafCardRendererAdapter` (outbound adapter).
  - FlyingSaucer `Java2DRenderer` integration for 1000x1250 PNG image rendering.
- [x] **Dual Template Architecture (Render vs Preview)**:
  - `generic_2_fields_template.html`: High-resolution canvas (1000x1250) for pixel-perfect PNG card rendering.
  - `preview_card_template.html`: Legible, interactive in-browser editor allowing real-time text customization before export.
- [x] **Database & Repository Extension**:
  - Updated `DutyRepositoryPort`, `DutyRepository`, and `DutyRepositoryJpa` with `findById`, `update`, `findCurrentMonthDutyByType`, and `findLatestByDutyType`.
  - Updated `DutyEventRepositoryPort` and `ThemeRepositoryPort` with `findById` and `update` capabilities.
- [x] **HATEOAS Hypermedia Integration**:
  - Integrated `spring-boot-starter-hateoas`.
  - Built `DutyModelAssembler` generating HAL compliant `_links` (`self`, `card-render`, `card-preview`, `all-duties`).
  - Updated `DutyResource` to return `201 Created` with `Location` header and HATEOAS HAL JSON payload.
- [x] **Automated Field Deduction (Simplicity)**:
  - `period` automatically derived via `SemesterEnum.from(date)` (Months 1–6 $\rightarrow$ `FIRST_SEMESTER`, Months 7–12 $\rightarrow$ `SECOND_SEMESTER`).
  - `year` derived automatically from `date.year`.
  - `year` field removed from `DutyRequestDto` payload.
- [x] **Unified Controller Routes (Zero Duplication)**:
  - `CardResource`: Unified `/cards/render` and `/cards/render/{dutyId}`, `/cards/preview` and `/cards/preview/{dutyId}`.
  - `DutyResource`, `ThemeResource`, `DutyEventResource`, `TemplateResource`: Unified `/resource` and `/resource/{id}` endpoints.
- [x] **Database Template Association, Random Selection & PNG Upload**:
  - Flyway migration `V3__Add_Template_Table.sql` creating `template` database table with foreign key `theme_id`.
  - Added `Template` domain model, `TemplateEntity` JPA entity, `TemplateRepositoryJpa`, `TemplateRepositoryPort`, `TemplateRepository`, `TemplateUsecase`, and `TemplateResource`.
  - Full CRUD REST API at `/templates` supporting PNG image file uploads.
  - Updated `CardUsecase` to query templates by `theme.id` and select a template **at random** (`dbTemplates.random()`) when multiple templates exist for the theme.
- [x] **Automated Unit & Integration Test Suite**:
  - `CardUsecaseTest`: Tests actual month `GIRA_ABERTA` fallback, specific `dutyId` rendering, random template selection, and missing duty exception.
  - `CardResourceTest`: Tests preview and PNG render REST responses.
  - `DutyResourceTest`: Tests HATEOAS HAL link generation (`_links`).
  - `SemesterEnumTest`: Tests automatic date-to-semester mapping across months.
  - `TemplateResourceTest`: Tests template listing, multipart PNG upload creation, and deletion.

---

## 6. Execution & Testing Command

To build the project and execute all unit test suites:
```bash
./gradlew test
```

