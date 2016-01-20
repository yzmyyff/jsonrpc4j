package com.yzmy.jsonrpc4j.jacksonfeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzmy.jsonrpc4j.JsonRpcBasicServer;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
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
            Assert.assertThat(baos.toString(), JUnitMatchers.containsString("JsonMappingException"));
        } catch (JsonMappingException e) {
            // 应该抛出这个错误.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
