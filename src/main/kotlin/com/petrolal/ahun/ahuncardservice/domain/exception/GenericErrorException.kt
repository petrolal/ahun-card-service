package com.petrolal.ahun.ahuncardservice.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class GenericErrorException(override val message: String) : RuntimeException(message)