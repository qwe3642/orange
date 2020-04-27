package com.fruit.template.email.init;


import com.fruit.template.email.dto.MailDto;
import com.fruit.template.email.service.MailService;
import com.fruit.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 邮件应用初始化
 */
@Component
public class MailApplication implements ApplicationRunner {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtil RedisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<MailDto> mailDtoList =mailService.queryByList();
        mailDtoList.stream().forEach(o->{
            RedisUtil.set(o.getId(),o);
        });
    }
}
