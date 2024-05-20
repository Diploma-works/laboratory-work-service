package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.service.LabWorkTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lab-work-tasks")
public class LabWorkTaskController {

    private final LabWorkTaskService labWorkTaskService;

    @GetMapping("/{labWorkId}/{variant}")
    public ResponseEntity<?> getLabWorkTask(@PathVariable Integer labWorkId, @PathVariable Integer variant) {
        return ResponseEntity.ok(labWorkTaskService.getLabWorkTask(labWorkId, variant));
    }

    @DeleteMapping("reset-variants")
    public ResponseEntity<?> resetVariants() {
        labWorkTaskService.resetVariants();
        return ResponseEntity.ok().build();
    }

}

