/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  LogMapper.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-04-23 15:06:39 星期四
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.template.email.mapper;


import com.fruit.template.email.dto.LogDto;

import java.util.List;

/**
 * <p>项目名称：email<p>
 * <p>类名称：LogMapper<p>
 * <p>类描述：邮件推送日志Mapper<p>
 *
 * @author 创建人：zhangx
 * @author 创建时间：2020-04-23 15:06:39 星期四
 * @author 修改人：zhangx
 * @author 修改时间：2020-04-23 15:06:39 星期四
 * @author 修改备注：
 */
public interface LogMapper {

    String test();

    void updateStatus(LogDto msgLog);

    LogDto selectByPrimaryKey(String msgId);

    List<LogDto> selectTimeoutMsg();

    void updateTryCount(LogDto msgLog);

    void insert(LogDto msgLog);

    String testSql();
}
