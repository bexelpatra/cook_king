package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public LifeCycleTest lifeCycleTest() {
        return new LifeCycleTest();
    }

    public class LifeCycleTest {

        public void init(){
            System.out.println("init");
        }
        public void destroy() throws Exception {
            System.out.println("destroy");
        }

    }
}
