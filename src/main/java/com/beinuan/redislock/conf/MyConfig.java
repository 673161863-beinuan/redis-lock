package com.beinuan.redislock.conf;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname Config
 * @Author: Zhao Minglei
 * @Date: 2020/5/16 0016 19:55
 */
@Configuration
public class MyConfig {


    @Bean
    public Redisson redisson(){
        Config config = new Config();
        //主从(单机)
        config.useSingleServer().setAddress("redis://192.168.25.138:6379").setDatabase(0);
        //哨兵
//    config.useSentinelServers().setMasterName("mymaster");
//    config.useSentinelServers().addSentinelAddress("redis://192.168.1.1:26379");
//    config.useSentinelServers().addSentinelAddress("redis://192.168.1.2:26379");
//    config.useSentinelServers().addSentinelAddress("redis://192.168.1..3:26379");
//    config.useSentinelServers().setDatabase(0);
//    //集群
//    config.useClusterServers()
//            .addNodeAddress("redis://192.168.0.1:8001")
//            .addNodeAddress("redis://192.168.0.2:8002")
//            .addNodeAddress("redis://192.168.0.3:8003")
//            .addNodeAddress("redis://192.168.0.4:8004")
//            .addNodeAddress("redis://192.168.0.5:8005")
//            .addNodeAddress("redis://192.168.0.6:8006");
//    config.useSentinelServers().setPassword("xiaocai");//密码设置
        return (Redisson) Redisson.create(config);
    }
}
