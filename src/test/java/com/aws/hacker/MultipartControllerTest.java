package com.aws.hacker;

import com.aws.hacker.multipart.controller.MultipartController;
import com.aws.hacker.multipart.service.TensorFlowService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MultipartController.class)
public class MultipartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TensorFlowService tensorFlowService;

    @Test
    public void testUploadImg() throws Exception {
        // 임시로 MultiPartFile을 준비하고 Flask와의 통신을 모킹
        Mockito.when(tensorFlowService.predict(Mockito.any(MultipartFile.class)))
                .thenReturn(new float[]{0.1f, 0.2f, 0.3f});

        // MockMultipartFile을 생성합니다.
        MockMultipartFile mockFile = new MockMultipartFile(
                "files",                    // Form field name
                "test-image.jpg",            // Original filename
                "image/jpeg",                // Content type
                "Test Image Content".getBytes()  // File content as byte array
        );

        // /upload로 요청을 보내고 MultiPartFile을 정상적으로 받는지 테스트합니다.
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockFile))
                .andExpect(status().isOk());

        // TensorFlowService의 predict 메소드가 호출되었는지 확인
        Mockito.verify(tensorFlowService).predict(Mockito.any(MultipartFile.class));
    }
}
