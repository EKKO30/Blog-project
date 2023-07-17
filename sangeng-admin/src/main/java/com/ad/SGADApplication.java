package com.ad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.ad.*","com.fw.*"})
//用于MP
@MapperScan("com.fw.mapper")
public class SGADApplication {
    public static void main(String[] args) {
        SpringApplication.run(SGADApplication.class,args);
    }
}

