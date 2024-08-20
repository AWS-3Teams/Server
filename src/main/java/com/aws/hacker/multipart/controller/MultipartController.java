package com.aws.hacker.multipart.controller;

import com.aws.hacker.multipart.service.TensorFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MultipartController {

    private final TensorFlowService tensorFlowService;

    @RequestMapping("/upload")
    public ResponseEntity<float[]> uploadImg(@RequestPart List<MultipartFile> files) {
        MultipartFile uploadedFile = files.get(0);
        try {
            // 멀티파트 파일을 이미지로 로드합니다.
            BufferedImage image = convertToBufferedImage(uploadedFile.getInputStream());
            // 이미지를 TensorFlow 입력 형식에 맞게 전처리합니다.
            float[] imageData = preprocessImage(image);
            // TensorFlow 모델에 입력하여 결과를 얻습니다.
            float[] predictions = tensorFlowService.predict(imageData);
            // 예측 결과를 응답으로 전송합니다.
            return ResponseEntity.ok(predictions);
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일을 처리하는 중 오류가 발생했습니다.", e);
        }
    }

    private BufferedImage convertToBufferedImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    private float[] preprocessImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float[] imageData = new float[width * height * 3]; // RGB 값을 위한 3 채널
        int[] rgbArray = image.getRGB(0, 0, width, height, null, 0, width);
        for (int i = 0; i < rgbArray.length; i++) {
            int pixel = rgbArray[i];
            imageData[i * 3] = ((pixel >> 16) & 0xFF) / 255.0f; // Red
            imageData[i * 3 + 1] = ((pixel >> 8) & 0xFF) / 255.0f; // Green
            imageData[i * 3 + 2] = (pixel & 0xFF) / 255.0f; // Blue
        }
        return imageData;
    }
}
