package com.aws.hacker.multipart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aws.hacker.multipart.service.TensorFlowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MultipartController {

    private final TensorFlowService tensorFlowService;

    @RequestMapping("/upload")
    public ResponseEntity<float[]> uploadImg(@RequestPart List<MultipartFile> files) {
        MultipartFile uploadedFile = files.get(0);
        System.out.println(uploadedFile.getOriginalFilename());

        // Flask 서버에 파일을 전송하고 예측 결과를 받습니다.
        float[] predictions = tensorFlowService.predict(uploadedFile);

        // 예측 결과를 클라이언트로 전송합니다.
        System.out.println("Predictions: " + predictions.length + " values");

        // 첫 번째 모델의 예측 값 처리 (단일 값)
        if (predictions.length == 1) {
            return ResponseEntity.ok(predictions);
        }
        else{
            return ResponseEntity.ok(predictions);
        }
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    private float calculatePercentile(float n, float min, float max) {
        if (n < min) {
            return 0;
        }
        if (n > max) {
            return 100;
        }
        return 100 - ((n - min) / (max - min) * 100);
    }
}
