package com.petrolal.ahun.ahuncardservice.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(override val message: String) : RuntimeException(message)