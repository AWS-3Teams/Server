package com.aws.hacker.example.service;

import com.aws.hacker.example.dto.RequestCreateDto;
import com.aws.hacker.example.dto.RequestUpdateNameDto;
import com.aws.hacker.example.dto.ResponseDto;
import com.aws.hacker.example.entity.Example;
import com.aws.hacker.example.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepository exampleRepository;

    // 한개 조회
    public ResponseDto findExampleById(Long id) {
        Example example = exampleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없어용"));
        return new ResponseDto(example.getId(), example.getName(), example.getAge());
    }

    // 모두 조회
    public List<ResponseDto> findAll(){
        return exampleRepository.findAll()
                .stream().map(e -> new ResponseDto(e.getId(), e.getName(), e.getAge())).toList();
    }

    // 생성
    public void createExample(RequestCreateDto dto){
        exampleRepository.save(Example.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .build());
    }

    // 수정
    public void updateNameExample(Long id, RequestUpdateNameDto dto){
        Example example = exampleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없어용"));
        example.setName(dto.getName());
        exampleRepository.save(example);
    }

    // 삭제
    public void deleteExample(Long id){
        exampleRepository.deleteById(id);
    }
}
