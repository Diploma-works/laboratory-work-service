package com.vko.labworkproducer.service;

import com.vko.labworkproducer.dto.TopicDto;
import com.vko.labworkproducer.entity.Topic;
import com.vko.labworkproducer.exception.TopicAlreadyExistsException;
import com.vko.labworkproducer.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> getTopicList() {
        return topicRepository.findAll();
    }

    public Topic createTopic(TopicDto dto) {
        Topic topic = new Topic(dto.name());
        if (Optional.ofNullable(topicRepository.findByName(topic.getName())).isPresent()) {
            throw new TopicAlreadyExistsException();
        }
        return topicRepository.save(topic);
    }

    public List<Topic> getTopicListByTopicDtoList(List<TopicDto> topicDtoList) {
        List<String> topicNamesList = topicDtoList.stream()
                .map(TopicDto::name)
                .collect(Collectors.toList());

        // Находим все существующие в базе Topic с именами из листа
        List<Topic> existingTopics = topicRepository.findAllByNameList(topicNamesList);

        Map<String, Topic> existingTopicsMap = existingTopics.stream()
                .collect(Collectors.toMap(Topic::getName, topic -> topic));

        List<Topic> topics = new ArrayList<>();

        for (String name : topicNamesList) {
            // Если такого Topic'а в базе нет, то создаем новый и добавляем его в список
            if (!existingTopicsMap.containsKey(name)) {
                Topic newTopic = new Topic(name);
                topics.add(newTopic);
                existingTopicsMap.put(name, newTopic);
            } else {
                topics.add(existingTopicsMap.get(name));
            }
        }

        // Сохраняем все новые Topic в базу
        topicRepository.saveAll(topics);

        return topics;
    }

    public void deleteTopicById(Integer id) {
        topicRepository.deleteById(id);
    }

    public Topic findTopicById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.get();
    }

}
