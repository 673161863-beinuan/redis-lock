package com.beinuan.redislock.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Classname RedisLockController
 * @Author: Zhao Minglei
 * @Date: 2020/5/16 0016 14:53
 */

@RestController
public class RedisLockController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Redisson redisson;

    @GetMapping(value = "/getLock")
    public String getLock() {
        String lockKey = "lock";
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            redissonLock.lock();
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("售卖成功,剩余" + realStock + "");

                return "success";
            } else {
                System.out.println("剩余库存不足");
                return "fail";
            }
        } finally {
            redissonLock.unlock();
        }
    }


    // 代码太多
  /*  @RequestMapping("/getLock")
    public String getLock() {
        String key = "lock";
        String lockVlue = UUID.randomUUID().toString();
        // 返回是否创建成功
        // true 创建成功
        // false 创建失败
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(key, lockVlue,10, TimeUnit.SECONDS);
        try {
            // 如果创建成功 lock==true 取反为false，则顺利进行购票
            // 如果创建不成功 lock==false 取反为true 直接走进if语句 结束
            if (!lock) {
                return "error";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("售卖成功... 还剩" + realStock + "个");
                // int i = 1/0;
                return "success";
            } else {
                System.out.println("库存不足");
                return "failed";
            }
        } finally {
            if (lockVlue.equals(stringRedisTemplate.opsForValue().get(key))){
                stringRedisTemplate.delete(key);
            }
        }
    }*/



    // 如果finally没有执行，那么还是会造成死锁的现象
    // 设置过期时间会造成 无效锁，乱解锁的现象
    /*@RequestMapping("/getLock")
    public String getLock() {
        String key = "key";
        // 返回是否创建成功
        // true 创建成功
        // false 创建失败
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(key, "lock",10, TimeUnit.SECONDS);
        try {
            // 如果创建成功 lock==true 取反为false，则顺利进行购票
            // 如果创建不成功 lock==false 取反为true 直接走进if语句 结束
            if (!lock) {
                return "error";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("售卖成功... 还剩" + realStock + "个");
                // int i = 1/0;
                return "success";
            } else {
                System.out.println("库存不足");
                return "failed";
            }
        } finally {
            if (lock){
                stringRedisTemplate.delete(key);
            }
        }
    }*/
    // 这样解决了分布式并发 ，但是容易造成死锁
  /*  @RequestMapping("/getLock")
    public String getLock() {
        String key = "key";
        // 返回是否创建成功
        // true 创建成功
        // false 创建失败
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(key, "lock");
        // 如果创建成功 lock==true 取反为false，则顺利进行购票
        // 如果创建不成功 lock==false 取反为true 直接走进if语句 结束
        if (!lock) {
            return "error";
        }

        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
            System.out.println("售卖成功... 还剩" + realStock + "个");
            // int i = 1/0;
            stringRedisTemplate.delete(key);
            return "success";
        } else {
            System.out.println("库存不足");
            // 库存不足时候也要释放锁
            stringRedisTemplate.delete(key);
            return "failed";
        }
    }*/

        // 不能解决分布式的并发问题
   /* @RequestMapping("/getLock")
    public String getLock() {

        // 同步代码块
        synchronized (this) {

            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {

                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("售卖成功... 还剩" + realStock + "个");
                return "success";
            } else {

                System.out.println("库存不足");
                return "failed";
            }
        }
    }*/

    }
