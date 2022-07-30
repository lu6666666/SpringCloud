package com.study.controoler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configClient")
@RefreshScope //手动刷新 获取最新的配置文件信息
public class ConfigClientController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/getConfig")
    public String getConfig(){
        return port + "  " + name;
    }
}
