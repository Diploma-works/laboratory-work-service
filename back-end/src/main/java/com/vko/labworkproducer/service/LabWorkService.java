package com.vko.labworkproducer.service;

import com.vko.labworkproducer.dto.LabWorkDto;
import com.vko.labworkproducer.entity.LabWork;
import com.vko.labworkproducer.entity.Topic;
import com.vko.labworkproducer.exception.LabWorkAlreadyExistsException;
import com.vko.labworkproducer.repository.LabWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LabWorkService {

    private final LabWorkRepository labWorkRepository;
    private final TopicService topicService;

    public LabWork getLabWorkById(Integer id) {
        return labWorkRepository.findById(id).orElseThrow();
    }

    public List<LabWork> getLabWorkList() {
        return labWorkRepository.findAll();
    }

    public LabWork createLab(LabWorkDto dto) {
        List<Topic> topics = topicService.getTopicListByTopicDtoList(dto.topics());
        LabWork labWork = new LabWork(dto.name(), dto.description(), topics);
        if (Optional.ofNullable(findByName(labWork.getName())).isPresent()) {
            throw new LabWorkAlreadyExistsException();
        }
        return labWorkRepository.save(labWork);
    }

    public LabWork findByName(String name) {
        return labWorkRepository.findByName(name);
    }

    public LabWork updateLabWork(LabWork labWork) {
        if (Optional.ofNullable(findByName(labWork.getName())).isPresent()) {
            throw new LabWorkAlreadyExistsException();
        }
        return labWorkRepository.save(labWork);
    }

    public void deleteLabWorkById(Integer id) {
        labWorkRepository.deleteById(id);
    }

}
