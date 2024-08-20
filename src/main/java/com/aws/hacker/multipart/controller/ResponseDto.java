package com.aws.hacker.multipart.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private float[] value;
    private int type;

    public ResponseDto(float[] value, int type) {
        this.value = value;
        this.type = type;
    }
}
