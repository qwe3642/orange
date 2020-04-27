/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  LogAction.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-04-23 15:06:39 星期四
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.template.email.action;


import com.fruit.template.email.dto.LogDto;
import com.fruit.template.email.dto.ServerResponse;
import com.fruit.template.email.service.LogService;
import com.fruit.template.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>项目名称：email<p>
 * <p>类名称：LogAction<p>
 * <p>类描述：邮件推送日志Action<p>
 *
 * @author 创建人：zhangx
 * @author 创建时间：2020-04-23 15:06:39 星期四
 * @author 修改人：zhangx
 * @author 修改时间：2020-04-23 15:06:39 星期四
 * @author 修改备注：
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/email")
public class LogAction {

    // Servrice start
    @Autowired(required = false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
    private LogService logService;

    @Autowired
    private MailService mailService;

    /**
     * @param @param  model
     * @param @param  request
     * @param @return
     * @param @throws Exception
     * @return String 返回类型
     * @throws
     * @Title: index
     * @Description: 首页
     */
    @RequestMapping("/index")
    public String index(LogDto dto, HttpServletRequest request) throws Exception {
        return "zx/email/log/index";
    }

    /**
     * @param @param  model
     * @param @param  request
     * @param @return
     * @param @throws Exception
     * @return String 返回类型
     * @throws
     * @Title: test
     * @Description: 测试
     */
    @GetMapping("/test")
    public ServerResponse test(@RequestParam(value = "mailId")String mailId, HttpServletRequest request) throws Exception {
        return mailService.sendMaill(mailId);
    }

}
