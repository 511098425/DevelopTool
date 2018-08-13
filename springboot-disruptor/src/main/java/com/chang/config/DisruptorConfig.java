package com.chang.config;

import com.lmax.disruptor.spring.boot.DisruptorTemplate;
import com.lmax.disruptor.spring.boot.event.DisruptorBindEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @title:
 * @description: disruptor队列配置
 * @author: Mr.Chang
 * @create: 2018-08-10
 * @modify:
 **/
@Configuration
@EnableScheduling
public class DisruptorConfig {

  @Autowired
  private DisruptorTemplate disruptorTemplate;

  private volatile CountDownLatch countDownLatch = new CountDownLatch(1000000);

  @Scheduled(fixedDelay = 30000) // 每1s执行1次
  public void send() throws Exception {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          for (int i = 0; i < 100; i++) {
            DisruptorBindEvent event = new DisruptorBindEvent(this, "message " + countDownLatch.getCount());

            event.setEvent("Event-Output");
            event.setTag("Email-Output");
            event.setKey("id-" + countDownLatch.getCount());

            disruptorTemplate.publishEvent(event);
            countDownLatch.countDown();
          }
        }
      });
      thread.start();

  }
/*
  @Scheduled(fixedDelay = 1000) // 每1s执行1次
  public void send2() throws Exception {

    DisruptorBindEvent event = new DisruptorBindEvent(this, "message " + Math.random());

    event.setEvent("Event-Output");
    event.setTag("Sms-Output");
    event.setKey("id-" + Math.random());

    disruptorTemplate.publishEvent(event);

  }*/

}
