/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  XtJsService.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-05-13 13:50:34 星期三
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.adminuser.system.service;

import com.fruit.adminuser.system.dto.XtJsDto;
import com.fruit.adminuser.system.mapper.XtJsMapper;
import com.fruit.basedata.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>类描述： 角色Service<p>
 * <p>项目名称：admin-user<p> 
 * <p>类名称：XtJsService<p>
 * @author 创建人：zhangx 
 * @author 创建时间：2020-05-13 13:50:34 星期三
 * @author 修改人：zhangx
 * @author 修改时间：2020-05-13 13:50:34 星期三
 * @author 修改备注：
 * 
 * @version
 * 
 */
@Service
public class XtJsService extends BaseService<XtJsDto> {
	
	@Autowired
    private XtJsMapper mapper;
		
	public XtJsMapper getMapper() {
		return mapper;
	}

	public Boolean login(String username, String password) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("username",username);
		params.put("password",password);
		Integer count=mapper.login(params);
		if(count>0) return true;
		return false;
	}
}