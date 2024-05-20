package com.vko.labworkproducer.service;

import com.vko.labworkproducer.dto.TaskDto;
import com.vko.labworkproducer.entity.Task;
import com.vko.labworkproducer.entity.Topic;
import com.vko.labworkproducer.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TopicService topicService;

    public Task createTask(TaskDto dto) {
        Topic topic = topicService.findTopicById(dto.topic_id());
        Task task = new Task(dto.description(), topic);
        task = taskRepository.save(task);
        return task;
    }

    public List<Task> getRandomTaskListByTopicList(List<Topic> topics) {
        Integer[] ids = topics.stream()
                .map(Topic::getId)
                .toList().toArray(new Integer[0]);
        return taskRepository.findRandomTasksByTopics(ids);
    }

    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByTopicId(Integer topicId) {
        return taskRepository.findTasksByTopicId(topicId);
    }

}
