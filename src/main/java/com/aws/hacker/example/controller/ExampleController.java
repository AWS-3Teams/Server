package com.aws.hacker.example.controller;

import com.aws.hacker.example.dto.RequestCreateDto;
import com.aws.hacker.example.dto.RequestUpdateNameDto;
import com.aws.hacker.example.dto.ResponseDto;
import com.aws.hacker.example.service.ExampleService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleController {
    private final ExampleService exampleService;

    @GetMapping
    public ResponseEntity<ResponseDto> getExample(
            @Parameter(name="id", description = "예시 ID") @RequestParam("id") Long id) {
        return ResponseEntity.ok().body(exampleService.findExampleById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseDto>> getExamples(){
        return ResponseEntity.ok().body(exampleService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createExample(@Valid @RequestBody RequestCreateDto dto){
        exampleService.createExample(dto);
        return ResponseEntity.ok().body("Example created");
    }

    @PatchMapping
    public ResponseEntity<String> updateExample(
            @Parameter(name="id", description = "예시 ID") @RequestParam("id") Long id,
            @Valid @RequestBody RequestUpdateNameDto dto){
        exampleService.updateNameExample(id, dto);
        return ResponseEntity.ok().body("Example updated");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteExample(
            @Parameter(name="id", description = "예시 ID") @RequestParam("id") Long id){
        exampleService.deleteExample(id);
        return ResponseEntity.ok().body("Example deleted");
    }
}
