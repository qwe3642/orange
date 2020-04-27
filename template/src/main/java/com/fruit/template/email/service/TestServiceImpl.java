package com.fruit.template.email.service;

import com.fruit.config.RabbitConfig;
import com.fruit.template.email.dto.LogDto;
import com.fruit.template.email.dto.Mail;
import com.fruit.template.email.dto.ResponseCode;
import com.fruit.template.email.dto.ServerResponse;
import com.fruit.template.email.mapper.LogMapper;
import com.fruit.util.MessageHelper;
import com.fruit.util.RandomUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {
    @Autowired
    private LogMapper msgLogMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ServerResponse send() {
        Mail mail=new Mail();
        mail.setTo("364297143@qq.com");
        mail.setContent("正文");
        mail.setTitle("标题");
        String msgId = RandomUtil.UUID32();
        mail.setMsgId(msgId);

        LogDto msgLog = new LogDto(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);// 消息入库

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);// 发送消息

        return ServerResponse.success(ResponseCode.MAIL_SEND_SUCCESS.getMsg());
    }

    public String test() {
        return msgLogMapper.testSql();
    }
}
