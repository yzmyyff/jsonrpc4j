package com.yzmy.jsonrpc4j.rest.loadtest;

import com.yzmy.jsonrpc4j.ProxyUtil;
import com.yzmy.jsonrpc4j.loadtest.JsonRpcService;
import com.yzmy.jsonrpc4j.loadtest.ServletEngine;
import com.yzmy.jsonrpc4j.spring.rest.JsonRpcRestClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LoadRestTest {

    private ServletEngine servletEngine;

    @Before
    public void setup()
            throws Exception {
        servletEngine = new ServletEngine();
        servletEngine.startup();
    }

    @Test
    public void loadTestJSONRpcRestClient()
            throws MalformedURLException {
        JsonRpcRestClient jsonRpcRestClient = new JsonRpcRestClient(new URL(
                "http://127.0.0.1:" + ServletEngine.PORT + "/servlet"));
        JsonRpcService service = ProxyUtil.createClientProxy(
                JsonRpcService.class.getClassLoader(), JsonRpcService.class,
                jsonRpcRestClient);

        for (int i = 0; i < 100; i++) {
            service.doSomething();
            int simple = service.returnSomeSimple(i);
            assertEquals(i, simple);
            final String string = "--" + i;
            JsonRpcService.ComplexType complex = service.returnSomeComplex(i, string);
            assertNotNull(complex);
            assertEquals(i, complex.getInteger());
            assertEquals(string, complex.getString());
            assertTrue(complex.getList().contains(string));

            final String errorMessage = "Test exception";
            try {
                service.throwSomeException(errorMessage);
            } catch (Exception ex) {
                assertEquals(UnsupportedOperationException.class, ex.getClass());
                assertEquals(errorMessage, ex.getMessage());
            }

        }
    }

    @After
    public void teardown()
            throws Exception {
        servletEngine.stop();
    }

}
