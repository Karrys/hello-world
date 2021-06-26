package com.kay.demo;

import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testa() {
        String url = "http://localhost:9001";
        String result = HttpUtil.get(url);
        System.out.println(result);
    }
}
