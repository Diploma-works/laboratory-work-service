package com.vko.labworkproducer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {

    private Integer answerId;

    private String description;

    private String dateTime;

    private String labWorkName;

    private String username;

    private Integer variant;

    private Boolean withFile;

    public AnswerResponseDto(Integer answerId, String description, String dateTime, Integer variant, Boolean withFile) {
        this.answerId = answerId;
        this.description = description;
        this.dateTime = dateTime;
        this.variant = variant;
        this.withFile = withFile;
    }
}
