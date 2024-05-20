package com.vko.labworkproducer.mapper;

import com.vko.labworkproducer.dto.AnswerDto;
import com.vko.labworkproducer.dto.AnswerResponseDto;
import com.vko.labworkproducer.entity.Answer;
import com.vko.labworkproducer.entity.LabWorkTask;
import com.vko.labworkproducer.entity.User;
import com.vko.labworkproducer.service.LabWorkTaskService;
import com.vko.labworkproducer.service.UserService;
import com.vko.labworkproducer.utils.FileConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    private final LabWorkTaskService labWorkTaskService;
    private final UserService userService;

    public Answer dtoToEntity(AnswerDto dto) {
            byte[] data = null;
            if (dto.file() != null) {
                data = FileConverter.multipartFileToByteArray(dto.file());
            }
            LabWorkTask labWorkTask = labWorkTaskService.findLabWorkTaskById(dto.labWorkTaskId());
            User user = userService.findByUsername(dto.username());
            return Answer.builder()
                    .data(data)
                    .filename(dto.filename())
                    .description(dto.description())
                    .dateTime(dto.dateTime())
                    .labWorkTask(labWorkTask)
                    .user(user)
                    .build();
    }

    public AnswerResponseDto entityToResponseDto(Answer answer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = answer.getDateTime().format(formatter);
        Boolean withFile = answer.getData() != null;
        return new AnswerResponseDto(answer.getId(), answer.getDescription(), formattedDateTime, answer.getLabWorkTask().getVariant(), withFile);
    }

}
