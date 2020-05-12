/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  ${className}Action.java
 * 描       述:  <描述>
 * 修改人:  ${username}
 * 修改时间:  ${currentDate}
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.${module}.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fruit.common.action.BaseAction;
import com.fruit.common.annotation.Auth;
import com.fruit.${module}.dto.${className}Dto;
import com.fruit.${module}.model.${className}Info;
import com.fruit.${module}.service.${className}Service;
import com.fruit.common.baseData.Pager;
import com.fruit.common.baseData.ReturnData;

/**
 * <p>项目名称：${projectName}<p> 
 * <p>类名称：${className}Action<p> 
 * <p>类描述：${codeName}Action<p>
 * @author 创建人：${username} 
 * @author 创建时间：${currentDate}
 * @author 修改人：${username}
 * @author 修改时间：${currentDate}
 * @author 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("/${modulePath}/${lowerName}") 
public class ${className}Action extends BaseAction{
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ${className}Service ${lowerName}Service; 
	
	/**
	 * @Title: index
	 * @Description: 首页
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="首页",type="View")
	@RequestMapping("/index") 
	public ModelAndView index(${className}Info model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("${modulePath}/${lowerName}/index",context); 
	}
	
	
	/**
	 * @Title: queryByList
	 * @Description: 查询列表数据
	 * @param @param model
	 * @param @param response
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="查询数据到列表",type="Json")
	@RequestMapping("/queryByList") 
	public @ResponseBody  ReturnData queryByList(${className}Info model,HttpServletResponse response) throws Exception{
		List<${className}Dto> dataList = ${lowerName}Service.queryByList(model);
		//设置页面数据
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("rows", dataList);
		return setReturnData("0000","",returnObject);
	}
	
	/**
	 * 
	 * @Title: queryByPage
	 * @Description: 查询分页数据
	 * @param @param model
	 * @param @param response
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="分页查询数据",type="Json")
	@RequestMapping("/queryByPage") 
	public @ResponseBody  ReturnData queryByPage(${className}Info model,HttpServletResponse response) throws Exception{
		Pager pager = ${lowerName}Service.selectPage(model);
		//设置页面数据
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("total",pager.getRowCount());
		returnObject.put("rows", pager.getResults());
		return setReturnData("0000","",returnObject);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 添加或修改数据
	 * @param @param dto
	 * @param @param typeIds
	 * @param @param response
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="保存数据",type="Json")
	@RequestMapping("/save")
	public @ResponseBody ReturnData save(${className}Dto dto,Integer[] typeIds,HttpServletResponse response) throws Exception{
		if(dto.getId() == null){
			${lowerName}Service.add(dto);
		}else{
			${lowerName}Service.updateBySelective(dto);
		}
		return setReturnData("0000","保存成功~",null);
	}
	
	/**
	 * 
	 * @Title: getId
	 * @Description: 根据ID获取数据对象
	 * @param @param id
	 * @param @param response
	 * @param @param request
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="查询数据到对象",type="Json")
	@RequestMapping("/queryById")
	public @ResponseBody ReturnData queryById(String id,HttpServletResponse response) throws Exception{
		${className}Dto dto  = ${lowerName}Service.queryById(id);
		if(dto == null){
			return setReturnData("0000","没有找到对应的记录!",null);
		}
		
		return setReturnData("0000","",dto);

	}
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param @param id
	 * @param @param response
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="删除数据",type="Json")
	@RequestMapping("/delete")
	public @ResponseBody ReturnData delete(String id,HttpServletResponse response) throws Exception{
		${lowerName}Service.delete("delete",id);
		return setReturnData("0000","删除成功~",null);
	}
	
	/**
	 * 
	 * @Title: batchDelete
	 * @Description: 批量删除
	 * @param @param id
	 * @param @param response
	 * @param @throws Exception
	 * @return void 返回类型
	 * @throws
	 */
	@Auth(verifyLogin=true,verifyURL=false,title="批量删除数据",type="Json")
	@RequestMapping("/batchDelete")
	public @ResponseBody ReturnData batchDelete(String ids,HttpServletResponse response) throws Exception{
		String[] idArray = ids.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for (String s : idArray){
			Long id = Long.valueOf(s);
			idlist.add(id);
		}
		${lowerName}Service.batchDelete(idlist);
		return setReturnData("0000","删除成功~",null);
	}

}
