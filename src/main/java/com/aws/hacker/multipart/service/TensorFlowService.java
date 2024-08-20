package com.aws.hacker.multipart.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;

import java.io.IOException;

@Service
public class TensorFlowService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskServerUrl = "http://localhost:5000/predict";

    public float[] predict(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 파일을 Flask 서버로 전송
            Resource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            HttpEntity<Resource> requestEntity = new HttpEntity<>(resource, headers);

            ResponseEntity<float[]> responseEntity = restTemplate.exchange(
                    flaskServerUrl,
                    HttpMethod.POST,
                    requestEntity,
                    float[].class
            );

            return responseEntity.getBody();
        } catch (IOException e) {
            throw new RuntimeException("파일을 Flask 서버로 전송하는 중 오류가 발생했습니다.", e);
        }
    }
}
