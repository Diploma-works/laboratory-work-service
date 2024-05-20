package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.dto.TaskDto;
import com.vko.labworkproducer.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/get-tasks-by-topic-id/{topicId}")
    public ResponseEntity<?> getTasksByTopicId(@PathVariable Integer topicId) {
        return ResponseEntity.ok(taskService.getTasksByTopicId(topicId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

}
