/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  ${className}Dict.java
 * 描       述:  <描述>
 * 修改人:  ${username}
 * 修改时间:  ${currentDate}
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.${module}.common.dictdata;

import java.util.List;

import com.fruit.dict.common.DictDataBase;
import com.fruit.dict.common.DictDataException;
import com.fruit.${module}.dto.${className}Dto;
import com.fruit.${module}.model.${className}Info;
import com.fruit.${module}.service.${className}Service;

/**
 * <p>项目名称：${projectName}<p> 
 * <p>类名称：${className}Dict<p> 
 * <p>类描述： ${codeName}字典<p>
 * @author 创建人：${username} 
 * @author 创建时间：${currentDate}
 * @author 修改人：${username}
 * @author 修改时间：${currentDate}
 * @author 修改备注：
 * 
 * @version
 * 
 */
public class ${className}Dict extends DictDataBase<${className}Dto, ${className}Service> {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * 默认构造函数
	 */
	public ${className}Dict(String dictname, String dictdes) {
		super(dictname, dictdes);
	}

	/** {@inheritDoc} */
	
	protected List<${className}Dto> getData() {
		List<${className}Dto> list = null; 
		try {
			${className}Info params = new ${className}Info();
			params.setOrder("id asc");
			list = service.queryByList(params);
		} catch (Exception e) {
			new DictDataException("获取数据发生错误!getdata", e);
		}
		return list;
	}
	
	/**
	 * 取得当前行中CODE
	 * @param data 字典数据当前行数据
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	public String getCode(${className}Dto data) {
		return data.getCode();
	}		
	
	/**
	 * 取得当前行中NAME
	 * @param data 字典数据当前行数据
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	protected String getName(${className}Dto data) {
		return data.getName();
	}
}
