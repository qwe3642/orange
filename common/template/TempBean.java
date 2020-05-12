/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  ${className}Dto.java
 * 描       述:  <描述>
 * 修改人:  ${username}
 * 修改时间:  ${currentDate}
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.${module}.dto;

import java.util.Date;

import com.fruit.common.baseData.BasDto;
import com.fruit.${module}.model.${className}Info;
/**
 * <p>项目名称：${projectName}<p> 
 * <p>类名称：${className}Dto<p> 
 * <p>类描述：${codeName}Dto<p>
 * @author 创建人：${username} 
 * @author 创建时间：${currentDate}
 * @author 修改人：${username}
 * @author 修改时间：${currentDate}
 * @author 修改备注：
 * 
 * @version
 * 
 */
public class ${className}Dto extends BasDto {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	${feilds}
	
	/**
	 * ToModel
	 */
	public ${className}Info toModel(){
		${className}Info model = new ${className}Info();
		model.setId(this.getId());
		model.setCode(this.getCode());
		model.setName(this.getName());
		model.setFlag(this.getFlag());
		model.setLrr(this.getLrr());
		model.setLrsj(this.getLrsj());
		model.setXgr(this.getXgr());
		model.setXgsj(this.getXgsj());
		model.setYxbz(this.getYxbz());
		model.setScbz(this.getScbz());
		model.setBz(this.getBz());
		${toModel}
		
		return model;
	}
}
