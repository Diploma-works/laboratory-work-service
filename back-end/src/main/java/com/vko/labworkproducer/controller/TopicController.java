package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.dto.TopicDto;
import com.vko.labworkproducer.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @GetMapping("")
    public ResponseEntity<?> getTopicList() {
        return ResponseEntity.ok(topicService.getTopicList());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTopic(@RequestBody TopicDto topicDto) {
        return new ResponseEntity<>(topicService.createTopic(topicDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer id) {
        topicService.deleteTopicById(id);
        return ResponseEntity.ok().build();
    }

}
