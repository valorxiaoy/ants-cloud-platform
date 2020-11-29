package com.ants.mini.programs.config.bootstrap;

import com.ants.mini.programs.config.sentinel.SentinelConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统初始化
 *
 * @author Yueyang
 * @create 2020-11-18 20:07
 **/
@Component
public class SystemInfo implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SentinelConfig.initFlowRules();
        SentinelConfig.initDegradeRules();
    }
}