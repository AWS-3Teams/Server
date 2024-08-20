package com.aws.hacker;

import com.aws.hacker.multipart.service.TensorFlowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class TensorFlowServiceTest {

    @Autowired
    private TensorFlowService tensorFlowService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // ReflectionTestUtils를 사용하여 TensorFlowService의 RestTemplate 필드를 모킹된 객체로 교체
        ReflectionTestUtils.setField(tensorFlowService, "restTemplate", restTemplate);
    }

    @Test
    public void testPredict() throws Exception {
        // Mock MultipartFile 준비
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "Test Image Content".getBytes()
        );

        // 예상되는 응답 정의
        float[] mockResponse = new float[]{0.1f, 0.2f, 0.3f};

        // RestTemplate의 exchange 메소드를 모킹하여 실제로 HTTP 요청이 발생하지 않도록 설정
        Mockito.when(restTemplate.exchange(
                eq("http://localhost:5000/predict"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(float[].class)
        )).thenReturn(new ResponseEntity<>(mockResponse, org.springframework.http.HttpStatus.OK));

        // 서비스 메소드 호출 및 검증
        float[] predictions = tensorFlowService.predict(mockFile);
        assertArrayEquals(mockResponse, predictions);

        // RestTemplate의 exchange 메소드가 정확히 한 번 호출되었는지 검증
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(
                eq("http://localhost:5000/predict"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(float[].class)
        );
    }
}
