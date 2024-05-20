package com.vko.labworkproducer.dto;

import java.util.List;

public record LabWorkTaskDto(Integer id, List<TaskDto> taskDtoList) {
}
