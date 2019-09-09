package com.maywide.tool.mq.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.tool.mq.MQProducer;


@Service
public class MQProducerImpl implements MQProducer{
	
	private AmqpTemplate amqpTemplate;
	
	@Override
	public void sendDataToQueue(String queueKey, Object object) {
		System.out.println("--"+amqpTemplate);
        try {
        	amqpTemplate();
        	if(amqpTemplate != null) {
        		 amqpTemplate.convertAndSend(queueKey,JSONUtil.serialize(object));
                 System.out.println("------------消息发送成功");
        	}else {
        		System.out.println("------------amqpTemplate 为空");
        	}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private void amqpTemplate() {
		if(amqpTemplate == null) {
			amqpTemplate = SpringContextHolder.getApplicationContext().getBean(AmqpTemplate.class);
		}
	}

}
