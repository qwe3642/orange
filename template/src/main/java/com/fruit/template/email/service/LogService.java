/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  LogService.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-04-23 15:06:39 星期四
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.template.email.service;


import com.fruit.template.email.dto.LogDto;
import com.fruit.template.email.mapper.LogMapper;
import com.fruit.util.JodaTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>类描述： 邮件推送日志Service<p>
 * <p>项目名称：email<p>
 * <p>类名称：LogService<p>
 *
 * @author 创建人：zhangx
 * @author 创建时间：2020-04-23 15:06:39 星期四
 * @author 修改人：zhangx
 * @author 修改时间：2020-04-23 15:06:39 星期四
 * @author 修改备注：
 */
@Service
public class LogService {

    @Autowired
    private LogMapper mapper;

    public LogMapper getMapper() {
        return mapper;
    }

    public String test() {
        return getMapper().test();
    }

    public void updateStatus(String msgId, Integer status) {
        LogDto msgLog = new LogDto();
        msgLog.setMsg_id(msgId);
        msgLog.setStatus(status);
        msgLog.setUpdate_time(new Date());
        mapper.updateStatus(msgLog);
    }

    public LogDto selectByMsgId(String msgId) {
        return mapper.selectByPrimaryKey(msgId);
    }

    public List<LogDto> selectTimeoutMsg() {
        return mapper.selectTimeoutMsg();
    }

    public void updateTryCount(String msgId, Date tryTime) {
        Date nextTryTime = JodaTimeUtil.plusMinutes(tryTime, 1);

        LogDto msgLog = new LogDto();
        msgLog.setMsg_id(msgId);
        msgLog.setNext_try_time(nextTryTime);

        mapper.updateTryCount(msgLog);
    }
}