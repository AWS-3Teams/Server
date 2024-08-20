package com.aws.hacker.multipart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MultipartController {
    @RequestMapping("/upload")
    public void uploadImg(@RequestPart List<MultipartFile> files) {
        MultipartFile uploadedFile = files.get(0);
        // 멀티파트 파일을 텐서플로우의 입력에 맞는 사진으로 변환 후 .pb파일에 넣는다.
    }
}
