package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.inbound.rest

import com.petrolal.ahun.ahundutyservice.application.usecases.TemplateUsecase
import com.petrolal.ahun.ahundutyservice.domain.Template
import com.petrolal.ahun.ahundutyservice.domain.Theme
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.util.*

class TemplateResourceTest {

    private lateinit var templateUsecase: TemplateUsecase
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        templateUsecase = mock()
        val resource = TemplateResource(templateUsecase)
        mockMvc = MockMvcBuilders.standaloneSetup(resource).build()
    }

    @Test
    fun `GET templates should return list of templates`() {
        val theme = Theme(UUID.randomUUID(), "Gira de Exu", null, LocalDateTime.now())
        val template = Template(UUID.randomUUID(), "Template Exu 1", "gira_exu.png", theme)

        whenever(templateUsecase.findAll()).thenReturn(listOf(template))

        mockMvc.perform(get("/templates"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Template Exu 1"))
            .andExpect(jsonPath("$[0].imagePath").value("gira_exu.png"))
    }

    @Test
    fun `POST templates with multipart file should create template`() {
        val themeId = UUID.randomUUID()
        val templateId = UUID.randomUUID()
        val theme = Theme(themeId, "Gira de Exu", null, LocalDateTime.now())
        val template = Template(templateId, "New Custom Template", "uploaded_custom.png", theme)

        val file = MockMultipartFile("file", "custom.png", "image/png", byteArrayOf(1, 2, 3, 4))

        whenever(templateUsecase.create(any(), anyOrNull(), any()))
            .thenReturn(template)

        mockMvc.perform(
            multipart("/templates")
                .file(file)
                .param("name", "New Custom Template")
                .param("themeId", themeId.toString())
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("New Custom Template"))
            .andExpect(jsonPath("$.imagePath").value("uploaded_custom.png"))
    }

    @Test
    fun `DELETE templates should return 204 No Content`() {
        val id = UUID.randomUUID()
        doNothing().whenever(templateUsecase).delete(id)

        mockMvc.perform(delete("/templates/$id"))
            .andExpect(status().isNoContent)

        verify(templateUsecase).delete(id)
    }
}
