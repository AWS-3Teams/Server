package com.aws.hacker.multipart.controller;

import com.aws.hacker.multipart.service.TensorFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MultipartController {

    private final TensorFlowService tensorFlowService;

    @RequestMapping("/upload")
    public ResponseEntity<float[]> uploadImg(@RequestPart List<MultipartFile> files) {
        MultipartFile uploadedFile = files.get(0);
        // Flask 서버에 파일을 전송하고 예측 결과를 받습니다.
        float[] predictions = tensorFlowService.predict(uploadedFile);
        // 예측 결과를 클라이언트로 전송합니다.
        return ResponseEntity.ok(predictions);
    }
}
