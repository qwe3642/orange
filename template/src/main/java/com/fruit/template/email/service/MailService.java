package com.fruit.template.email.service;


import com.fruit.template.email.dto.Mail;
import com.fruit.template.email.dto.MailDto;
import com.fruit.template.email.dto.ResponseCode;
import com.fruit.template.email.dto.ServerResponse;
import com.fruit.template.email.mapper.MailMapper;
import com.fruit.template.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private MailMapper mapper;


    @Autowired
    private MailUtil mailUtil;

    public MailMapper getMapper(){
        return mapper;
    }
    public List<MailDto> queryByList(){
        return mapper.queryByList();
    }

    /**
     * 根据邮件id发送邮件
     * @param mailId
     */
    public ServerResponse sendMaill(String mailId){
        Mail mail=mailUtil.convertToMail(mailId);
        mailUtil.insertAndProduct(mail);
        return ServerResponse.success(ResponseCode.MAIL_SEND_SUCCESS.getMsg());
    }
}
