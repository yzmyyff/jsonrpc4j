package com.yzmy.jsonrpc4j.jacksonfeature;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yzmy.jsonrpc4j.JsonRpcMethod;
import com.yzmy.jsonrpc4j.JsonRpcParam;

/**
 * jackson特性测试类.
 * Created by Kuang.Ru on 2016/1/19.
 */
public class Service {
    @JsonRpcMethod("testPrimitiveFailFeature")
    public String testPrimitiveFailFeature(@JsonRpcParam("id") int id) {
        return "success " + id;
    }
}

class Custom {
    private String s;

    public Custom(@JsonProperty("s") String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
