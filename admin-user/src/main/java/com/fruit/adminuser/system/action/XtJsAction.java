/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  XtJsAction.java
 * 描       述:  <描述>
 * 修改人:  zhangx
 * 修改时间:  2020-05-13 13:50:34 星期三
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.adminuser.system.action;

import com.fruit.adminuser.system.service.XtJsService;
import com.fruit.basedata.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>项目名称：admin-user<p> 
 * <p>类名称：XtJsAction<p> 
 * <p>类描述：角色Action<p>
 * @author 创建人：zhangx 
 * @author 创建时间：2020-05-13 13:50:34 星期三
 * @author 修改人：zhangx
 * @author 修改时间：2020-05-13 13:50:34 星期三
 * @author 修改备注：
 * 
 * @version
 * 
 */
@RestController
@RequestMapping("/adminuser/system/xtJs") 
public class XtJsAction extends BaseAction {
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private XtJsService xtJsService;

	/**
	 * @Title: index
	 * @Description: 登录页面
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@PostMapping("/login")
	public Boolean login(@RequestParam("username") String name, @RequestParam("password")String pwd){
		Boolean isTrue=xtJsService.login(name,pwd);
		return isTrue;
	}

}
