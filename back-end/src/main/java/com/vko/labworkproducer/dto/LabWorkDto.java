package com.vko.labworkproducer.dto;

import java.util.List;

public record LabWorkDto(String name,
                         String description,
                         List<TopicDto> topics) {
}
