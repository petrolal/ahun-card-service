package com.petrolal.ahun.ahundutyservice.infrastructure.persistence.repository

import com.petrolal.ahun.ahundutyservice.application.ports.TemplateRepositoryPort
import com.petrolal.ahun.ahundutyservice.domain.Template
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.TemplateEntity
import com.petrolal.ahun.ahundutyservice.infrastructure.persistence.entity.ThemeEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TemplateRepository(
    private val repositoryJpa: TemplateRepositoryJpa
) : TemplateRepositoryPort {

    override fun findAll(): List<Template> {
        return repositoryJpa.findAll()
            .map(TemplateEntity::toDomain)
    }

    override fun findById(id: UUID): Template? {
        return repositoryJpa.findById(id)
            .map(TemplateEntity::toDomain)
            .orElse(null)
    }

    override fun findByThemeId(themeId: UUID): List<Template> {
        return repositoryJpa.findByThemeId(themeId)
            .map(TemplateEntity::toDomain)
    }

    override fun save(template: Template): Template {
        val entity = TemplateEntity.toEntity(template)
        val saved = repositoryJpa.save(entity)
        return saved.toDomain()
    }

    override fun update(id: UUID, template: Template): Template {
        val existing = repositoryJpa.findById(id)
            .orElseThrow { ResourceNotFoundException("Template with id $id not found") }

        existing.name = template.name
        existing.imagePath = template.imagePath
        existing.theme = template.theme?.let { ThemeEntity.toEntity(it) }

        val updated = repositoryJpa.save(existing)
        return updated.toDomain()
    }

    override fun deleteById(id: UUID) {
        if (!repositoryJpa.existsById(id)) {
            throw ResourceNotFoundException("Template with id $id not found")
        }
        repositoryJpa.deleteById(id)
    }
}
