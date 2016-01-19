package com.yzmy.jsonrpc4j.jacksonfeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzmy.jsonrpc4j.JsonRpcBasicServer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 主要用于测试是否可以正常使用一些jackson的特性.
 * Created by Kuang.Ru on 2016/1/18.
 */
public class JsonRpcServerTest {
    /**
     * 测试能否正常使用Jackson的反序列化特性
     */
    @Test
    public void testDeserializeFeature() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        JsonRpcBasicServer jsonRpcServer = new JsonRpcBasicServer(mapper, new Service(), Service.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            jsonRpcServer.handle(new ClassPathResource("jackson/jacksonDeserialize.json").getInputStream(), baos);
            Assert.fail("必须得抛个错.");
        } catch (Exception e) {
            // 没抛就是错的
        }
    }
}
