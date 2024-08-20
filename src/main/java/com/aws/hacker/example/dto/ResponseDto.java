package com.aws.hacker.example.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private Long id;
    private String name;
    private int age;

    public ResponseDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
