package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.dto.LabWorkDto;
import com.vko.labworkproducer.entity.LabWork;
import com.vko.labworkproducer.service.LabWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lab-works")
public class LabWorkController {

    private final LabWorkService labWorkService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabWork(@PathVariable Integer id) {
        return ResponseEntity.ok(labWorkService.getLabWorkById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getLabWorkList() {
        return ResponseEntity.ok(labWorkService.getLabWorkList());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLabWork(@RequestBody LabWorkDto labWorkDto) {
        return new ResponseEntity<>(labWorkService.createLab(labWorkDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLabWork(@RequestBody LabWork labWork) {
        return ResponseEntity.ok(labWorkService.updateLabWork(labWork));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLabWork(@PathVariable Integer id) {
        labWorkService.deleteLabWorkById(id);
        return ResponseEntity.ok().build();
    }

}
