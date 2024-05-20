package com.vko.labworkproducer.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record AnswerDto(MultipartFile file, String description, LocalDateTime dateTime, Integer labWorkTaskId,
                        String username, String filename) {
}
