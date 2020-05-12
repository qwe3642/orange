/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  ${className}Service.java
 * 描       述:  <描述>
 * 修改人:  ${username}
 * 修改时间:  ${currentDate}
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.${module}.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fruit.${module}.mapper.${className}Mapper;
import com.fruit.${module}.dto.${className}Dto;
import com.fruit.common.exception.BusinessException;
import com.fruit.common.service.BaseService;
import com.fruit.system.dto.XtRyDto;
import com.fruit.system.utils.UserUtils;
/**
 * <p>类描述： ${codeName}Service<p>
 * <p>项目名称：${projectName}<p> 
 * <p>类名称：${className}Service<p>
 * @author 创建人：${username} 
 * @author 创建时间：${currentDate}
 * @author 修改人：${username}
 * @author 修改时间：${currentDate}
 * @author 修改备注：
 * 
 * @version
 * 
 */
@Service("com.fruit.${module}.service.${className}Service")
public class ${className}Service extends BaseService<${className}Dto> {
	
	@Autowired
    private ${className}Mapper mapper;
		
	public ${className}Mapper getMapper() {
		return mapper;
	}
}