/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  XtJsMapper.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-05-13 13:50:34 星期三
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.adminuser.system.mapper;

import com.fruit.adminuser.system.dto.XtJsDto;
import com.fruit.basedata.BaseMapper;

import java.util.Map;

/**
 * <p>项目名称：admin-user<p> 
 * <p>类名称：XtJsMapper<p> 
 * <p>类描述：角色Mapper<p>
 * @author 创建人：zhangx 
 * @author 创建时间：2020-05-13 13:50:34 星期三
 * @author 修改人：zhangx
 * @author 修改时间：2020-05-13 13:50:34 星期三
 * @author 修改备注：
 * 
 * @version
 * 
 */
public interface XtJsMapper extends BaseMapper<XtJsDto> {

    Integer login(Map<String, Object> params);
}
