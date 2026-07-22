package com.petrolal.ahun.ahundutyservice.infrastructure.adapters.outbound.rendering

import com.petrolal.ahun.ahundutyservice.application.ports.CardRenderPort
import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.xhtmlrenderer.swing.Java2DRenderer
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * Outbound adapter implementing [CardRenderPort] using Thymeleaf and FlyingSaucer (Java2DRenderer).
 */
@Component
class ThymeleafCardRendererAdapter(
    private val templateEngine: TemplateEngine
) : CardRenderPort {

    override fun renderHtml(templateName: String, variables: Map<String, Any>): String {
        val context = Context().apply {
            variables.forEach { (key, value) -> setVariable(key, value) }
        }
        return templateEngine.process(templateName, context)
    }

    override fun renderPng(templateName: String, variables: Map<String, Any>): ByteArray {
        val htmlContent = renderHtml(templateName, variables)
        val renderer = Java2DRenderer(htmlContent, 1000, 1250)
        val bufferedImage = renderer.image
        val baos = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", baos)
        return baos.toByteArray()
    }
}
