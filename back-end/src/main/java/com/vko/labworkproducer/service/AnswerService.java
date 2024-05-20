package com.vko.labworkproducer.service;

import com.vko.labworkproducer.dto.*;
import com.vko.labworkproducer.entity.Answer;
import com.vko.labworkproducer.mapper.AnswerMapper;
import com.vko.labworkproducer.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    public AnswerResponseDto createAnswer(AnswerDto dto) {
        Answer answer = answerMapper.dtoToEntity(dto);
        answer = answerRepository.save(answer);
        return answerMapper.entityToResponseDto(answer);
    }

    public DownloadFileResponseDto downloadFile(AnswerIdRequestDto answerIdRequestDto) {
        return answerRepository.downloadFile(answerIdRequestDto.answerId());
    }

    public List<AnswerResponseDto> getAnswerList() {
        return answerRepository.getAnswerList();
    }

    public List<AnswerResponseDto> getAnswerListByLabWorkId(Integer labWorkId) {
        return answerRepository.getAnswerListByLabWorkId(labWorkId);
    }

    public List<AnswerResponseDto> getAnswerListByLabWorkIdAndUsername(Integer labWorkId, String username) {
        return answerRepository.getAnswerListByLabWorkIdAndUsername(labWorkId, username);
    }

    public void deleteAnswerById(AnswerIdRequestDto answerIdRequestDto) {
        answerRepository.deleteById(answerIdRequestDto.answerId());
    }

}
