package com.fruit.template.util;


import com.fruit.config.RabbitConfig;
import com.fruit.template.email.dto.*;
import com.fruit.template.email.mapper.LogMapper;
import com.fruit.util.MessageHelper;
import com.fruit.util.RandomUtil;
import com.fruit.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MailUtil {
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LogMapper msgLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 根据maillid转为mail
     * @param mailId
     * @return
     */
    public Mail convertToMail(String mailId){
        MailDto mailDto=(MailDto)redisUtil.get(mailId);
        List<MailCsrDto> mailCsrDtos=mailDto.getCsrDtoList();
        List<MailSjrDto> mailSjrDtos=mailDto.getSjrDtoList();
        List<String> csr=new ArrayList<String>();
        List<String> sjr=new ArrayList<String>();
        for(int i = 0;i < mailCsrDtos.size();i++){
            csr.add(mailCsrDtos.get(i).getMaillAddress());
        }
        for(int i = 0;i < mailCsrDtos.size();i++){
            sjr.add(mailSjrDtos.get(i).getMaillAddress());
        }
        Mail mail=new Mail();
        mail.setToArray(sjr);
        mail.setCsrArray(csr);
        mail.setContent(mailDto.getNr());
        mail.setTitle(mailDto.getTitle());
        String msgId = RandomUtil.UUID32();
        mail.setMsgId(msgId);
        return mail;
    }

    /**
     * 消息入库并推送
     * @param mail
     */
    public void insertAndProduct(Mail mail){
        String msgId=mail.getMsgId();
        LogDto msgLog = new LogDto(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);// 消息入库

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);// 发送消息
    }

    /**
     * 发送简单邮件
     *
     * @param mail
     */
    public boolean send(Mail mail) {
        String to = mail.getTo();// 目标邮箱
        String title = mail.getTitle();// 邮件标题
        String content = mail.getContent();// 邮件正文

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo((String [])mail.getToArray().toArray());
        message.setCc((String [])mail.getCsrArray().toArray());
        message.setSubject(title);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("邮件发送成功");
            return true;
        } catch (MailException e) {
            log.error("邮件发送失败, to: {}, title: {}", to, title, e);
            return false;
        }
    }
}
