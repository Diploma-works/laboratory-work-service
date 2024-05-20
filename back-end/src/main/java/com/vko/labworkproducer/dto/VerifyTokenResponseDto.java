package com.vko.labworkproducer.dto;

import org.springframework.http.HttpStatus;

public record VerifyTokenResponseDto(HttpStatus status, String response) {
}
