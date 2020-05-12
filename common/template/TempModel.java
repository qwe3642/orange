/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  ${className}Info.java
 * 描       述:  <描述>
 * 修改人:  ${username}
 * 修改时间:  ${currentDate}
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.${module}.model;

import java.util.Date;

import com.fruit.common.baseData.BasInfo;
import com.fruit.${module}.dto.${className}Dto;
/**
 * <p>类描述：${codeName}Info<p>
 * <p>项目名称：${projectName}<p> 
 * <p>类名称：${className}Info<p> 
 * @author 创建人：${username} 
 * @author 创建时间：${currentDate}
 * @author 修改人：${username}
 * @author 修改时间：${currentDate}
 * @author 修改备注：
 * 
 * @version
 * 
 */
public class ${className}Info extends BaseInfo {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	${feilds}
	
	/**
	 * ToDto
	 */
	public ${className}Dto toDto(){
		${className}Dto dto = new ${className}Dto();
		dto.setId(this.getId());
		dto.setCode(this.getCode());
		dto.setName(this.getName());
		dto.setFlag(this.getFlag());
		dto.setLrr(this.getLrr());
		dto.setLrsj(this.getLrsj());
		dto.setXgr(this.getXgr());
		dto.setXgsj(this.getXgsj());
		dto.setYxbz(this.getYxbz());
		dto.setScbz(this.getScbz());
		dto.setBz(this.getBz());
		${toDto}
		
		return dto;
	}
}
