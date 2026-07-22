package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.outbound.rendering

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class ThymeleafCardRendererAdapterTest {

    private val templateEngine = SpringTemplateEngine().apply {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            templateMode = TemplateMode.HTML
            characterEncoding = "UTF-8"
        })
    }

    private val cardRendererAdapter = ThymeleafCardRendererAdapter(templateEngine)

    @Test
    fun `renderPng should output an image with 1080x1350 resolution`() {
        val variables = mapOf(
            "eventTitle" to "GIRA DE TESTE",
            "eventSubtitle" to "SÁBADO | 20 DE MAIO - 18H",
            "bgImageName" to "gira_de_exu_e_cura_2.png",
            "bgImageDataUri" to "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
        )

        val imageBytes = cardRendererAdapter.renderPng("2_fields_template", variables)
        assertNotNull(imageBytes)

        val bufferedImage = ImageIO.read(ByteArrayInputStream(imageBytes))
        assertNotNull(bufferedImage)
        assertEquals(1080, bufferedImage.width)
        assertEquals(1350, bufferedImage.height)
    }
}
