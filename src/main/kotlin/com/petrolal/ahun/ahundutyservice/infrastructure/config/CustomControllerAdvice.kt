package com.petrolal.ahun.ahundutyservice.infrastructure.config

import com.petrolal.ahun.ahundutyservice.domain.dto.ExceptionResponseDto
import com.petrolal.ahun.ahundutyservice.domain.exception.BadRequestException
import com.petrolal.ahun.ahundutyservice.domain.exception.GenericErrorException
import com.petrolal.ahun.ahundutyservice.domain.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun notFoundExceptionHandler(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<ExceptionResponseDto> {
        val errorBody = ExceptionResponseDto(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.reasonPhrase,
            ex.message,
        )

        return ResponseEntity(errorBody, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badREquestExceptionHandler(
        ex: BadRequestException,
        request: WebRequest
    ): ResponseEntity<ExceptionResponseDto> {
        val errorBody = ExceptionResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.reasonPhrase,
            ex.message,
        )

        return ResponseEntity(errorBody, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GenericErrorException::class)
    fun genericErrorExceptionHandler(
        ex: GenericErrorException,
        request: WebRequest
    ) : ResponseEntity<ExceptionResponseDto> {
        val errorBody = ExceptionResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            ex.message,
        )

        return ResponseEntity(errorBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}