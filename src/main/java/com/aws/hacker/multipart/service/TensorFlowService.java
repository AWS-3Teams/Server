package com.aws.hacker.multipart.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

@Service
public class TensorFlowService {
    private Graph graph;
    private Session session;

    public TensorFlowService() {
        // 모델 파일을 불러와 그래프를 생성합니다.
        try (InputStream is = getClass().getResourceAsStream("/model.pb")) {
            graph = new Graph();
            graph.importGraphDef(IOUtils.toByteArray(is));
            session = new Session(graph);
        } catch (IOException e) {
            throw new RuntimeException("모델 파일을 불러올 수 없습니다.", e);
        }
    }

    // 입력 데이터를 사용하여 머신 러닝 모델을 실행하고 결과를 반환합니다.
    public float[] predict(float[] inputData) {
        try (Tensor<Float> input = Tensor.create(new long[]{1, inputData.length}, FloatBuffer.wrap(inputData));
             Tensor<Float> output = session.runner().feed("input", input).fetch("output").run().get(0).expect(Float.class))
        {
            float[] result = new float[output.numElements()];
            output.copyTo(result);
            return result;
        }
    }
}