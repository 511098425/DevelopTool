package com.chang.handler;

import com.lmax.disruptor.spring.boot.annotation.EventRule;
import com.lmax.disruptor.spring.boot.event.DisruptorEvent;
import com.lmax.disruptor.spring.boot.event.handler.DisruptorHandler;
import com.lmax.disruptor.spring.boot.event.handler.chain.HandlerChain;

import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * @title:
 * @description:
 * @author: Mr.Chang
 * @create: 2018-08-10
 * @modify:
 **/
@Slf4j
@EventRule("/Event-Output/Email-Output/**")
@Component
public class EmailDisruptorHandler implements DisruptorHandler<DisruptorEvent> {

	@Override
	public void doHandler(DisruptorEvent event, HandlerChain<DisruptorEvent> handlerChain)
			throws Exception {
		log.info("邮件收到事件消息:{}", event.toString());
	}
}
