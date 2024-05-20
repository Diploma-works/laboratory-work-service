package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.dto.AnswerDto;
import com.vko.labworkproducer.dto.AnswerIdRequestDto;
import com.vko.labworkproducer.dto.DownloadFileResponseDto;
import com.vko.labworkproducer.dto.UsernameRequestDto;
import com.vko.labworkproducer.service.AnswerService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    public ResponseEntity<?> createAnswer(
            @Nullable @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("dateTime") LocalDateTime dateTime,
            @RequestParam("taskId") Integer taskId,
            @RequestParam("username") String username,
            @Nullable @RequestParam("filename") String filename) {
        return new ResponseEntity<>(
                answerService.createAnswer(new AnswerDto(file, description, dateTime, taskId, username, filename)),
                HttpStatus.CREATED);
    }

    @PostMapping("/download-file")
    public ResponseEntity<Resource> downloadFile(@RequestBody AnswerIdRequestDto answerIdRequestDto) {
        DownloadFileResponseDto downloadFileResponseDto = answerService.downloadFile(answerIdRequestDto);
        ByteArrayResource resource = new ByteArrayResource(downloadFileResponseDto.data());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFileResponseDto.filename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("")
    public ResponseEntity<?> listAnswers() {
        return ResponseEntity.ok(answerService.getAnswerList());
    }

    @GetMapping("/{labWorkId}")
    public ResponseEntity<?> listAnswersByLabWorkId(@PathVariable Integer labWorkId) {
        return ResponseEntity.ok(answerService.getAnswerListByLabWorkId(labWorkId));
    }

    @PostMapping("/{labWorkId}")
    public ResponseEntity<?> listAnswersByLabWorkId(@PathVariable Integer labWorkId,
                                                    @RequestBody UsernameRequestDto usernameRequestDto) {
        return ResponseEntity.ok(answerService.getAnswerListByLabWorkIdAndUsername(labWorkId, usernameRequestDto.user()));
    }

    @DeleteMapping("/delete-answer")
    public ResponseEntity<?> deleteAnswerById(@RequestBody AnswerIdRequestDto answerIdRequestDto) {
        answerService.deleteAnswerById(answerIdRequestDto);
        return ResponseEntity.ok().build();
    }

}
