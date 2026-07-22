# Card Generation Refactoring Plan (Finalized)

We will implement a new endpoint to generate PNG image cards based on a dynamic HTML/CSS template and existing PNG background images.

Following our analysis and alignment, we have selected **Option A: Playwright** to perform the HTML-to-PNG rendering on the backend. This allows us to use modern CSS (Flexbox, Grid, rounded corners, custom fonts, etc.) to generate premium quality cards without needing a dedicated frontend application.

---

## 1. Context & Available Assets
The templates card assets are stored locally on the machine under `/home/petrolal/Documents/AhunDocs/`.
These files include:
* `Gira_de_exu_e_cura_21-02-26.png`
* `Gira_de_Cura_caboclos_e_boiadeiros.png`
* `Atendimento_de_Cura.png`
* `Gira_de_Erês_e_cura.png`
* `Gira_de_pretos_velhos_e_cura.png`
* `Festa_de_Erês_e_pretos_velhos_instagram.png`
* `gira_de_fechamento.png`

These images will act as background assets for the dynamically generated cards.

---

## 2. Selected Approach: Playwright on the Backend
We will use **Playwright for Java** to run a headless Chromium instance to render the HTML/CSS template and capture a screenshot.

### Gradle Dependencies
We will add the Playwright dependency to `build.gradle.kts`:
```kotlin
implementation("com.microsoft.playwright:playwright:1.44.0")
```

---

## 3. Libraries & Tools Needed to Develop Templates
To design and develop the Thymeleaf HTML/CSS templates, we only need:

1. **Spring Boot Starter Thymeleaf** (Already in `build.gradle.kts`): 
   Used on the backend to dynamically parse variables into the HTML layout.
2. **Any standard Web Browser (Chrome, Firefox, Safari)**:
   Because Playwright runs a standard Chromium browser, you can develop and preview the template locally in your browser by saving it as a `.html` file.
3. **IDE Tooling (IntelliJ IDEA)**:
   IntelliJ has built-in Thymeleaf support providing autocomplete for `th:*` tags and model attributes.
4. **Base64 Encoding**:
   `java.util.Base64` (Built-in to Java/Kotlin JDK) is used to load and embed local template images directly into the HTML context as raw data URI streams (`src="data:image/png;base64,..."`), ensuring standard relative pathing works smoothly inside the headless browser.

---

## 4. Implementation Steps

### Step A: Path Configuration
We will define the path to the template card background images in `application.yaml`:
```yaml
ahun:
  cards:
    templates-path: ${CARDS_TEMPLATES_PATH:/home/petrolal/Documents/AhunDocs}
```

### Step B: HTML Template
We will create a Thymeleaf HTML template at `src/main/resources/templates/card-template.html`.
* The template will accept dynamic placeholders like duty title, date, period, and a list of events.
* Background images will be loaded dynamically from the configured path, converted to a Base64 data URL (`data:image/png;base64,...`), and injected into the template so that the headless browser can resolve them instantly.

### Step C: Database Port Extension
1. Update `DutyRepositoryPort` to include:
   ```kotlin
   fun findById(id: UUID): Duty?
   ```
2. Implement `findById(id: UUID)` in `DutyRepository` using `repository.findById(id)`.

### Step D: Usecase & Controller
1. Create `CardGenerationUsecase` under `com/petrolal/ahun/ahundutyservice/application/usecases/`:
   - Fetches the `Duty` domain object by ID.
   - Maps the duty type or theme to the corresponding background image file.
   - Converts the background image file to a Base64 data URL.
   - Compiles the Thymeleaf template with the model variables.
   - Calls the Playwright rendering service to capture the screenshot as a byte array.
2. Add a new endpoint to `DutyResource.kt`:
   ```kotlin
   @GetMapping("/{id}/card", produces = [MediaType.IMAGE_PNG_VALUE])
   fun generateCard(@PathVariable("id") id: UUID): ResponseEntity<ByteArray>
   ```

---

## 5. Execution
Ready to begin implementation.
