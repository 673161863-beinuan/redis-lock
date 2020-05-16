package com.beinuan.redislock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Classname RedisLockMain
 * @Author: Zhao Minglei
 * @Date: 2020/5/16 0016 14:51
 */
@SpringBootApplication
public class RedisLockMain {

    public static void main(String[] args) {
        SpringApplication.run(RedisLockMain.class, args);

    }

}
