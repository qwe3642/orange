package com.fruit.createsource;

import org.apache.velocity.VelocityContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */

public class CreateJava {
	
	/**
	 * 字段模式_驼峰模式
	 */
	public static final String FIELD_MODE_TF = "1";
	/**
	 * 字段模式_默认模式
	 */
	public static final String FIELD_MODE_DEF = "0";
	
//	private static ResourceBundle res = ResourceBundle.getBundle("DataSourceConfig");
//	private static String url = res.getString("gpt.url");
//	private static String username = res.getString("gpt.username");
//	private static String passWord = res.getString("gpt.password");
	// 项目跟路径路径，此处修改为你的项目路径
	//private static String rootPath = getRootPath();// "F:\\openwork\\open\\";

	// private static String actionPath = "E:\\src\\com\\timesoft\\";

    public void createSource(Map<String,String> args) throws Exception{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	CreateBean createBean = new CreateBean();
	createBean.setUseCamel(false);
	if (args.containsKey("codeMode")) {
		String codeMode = args.get("codeMode");
		if ("1".equals(codeMode)) {
			createBean.setUseCamel(true);
		}
	}
	createBean.setOracleInfo(args.get("url"), args.get("dbusername"), args.get("dbpassWord"));
	/** 此处修改成你的 表名 和 中文注释 ***/
	// 模块名称
	String projectName = args.get("projectName");//工程名：htta-system
	String module = args.get("module");// 模块包名：system
	String username = args.get("username");// 开发人员名字
	String currentDate = df.format(new Date());
	String tableName = args.get("tableName"); //表名 UT_XT_RYCD
	String codeName = args.get("codeName");// 中文注释
	codeName = new String(codeName.getBytes(),"utf-8");
	username = new String(username.getBytes(),"utf-8");
	String className = createBean.getTablesNameToClassName(tableName);
	String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
	Map<String, String> tableMap = createBean.getTableNames();
	if (tableMap.containsKey(tableName.toUpperCase())){
		codeName = tableMap.get(tableName.toUpperCase());
	}
	// 根路径
	String srcPath =String.format("D:\\SourceCode\\Java_SVN\\%s\\src",projectName); //本地代码路径：D:\\SourceCode\\Java_SVN\\htta-system\\src
	if (args.containsKey("srcPath")) {
		srcPath = String.format("%s\\%s\\src",args.get("srcPath"),projectName);
	}
	// 包路径
	String pckPath = srcPath + "\\main\\java\\com\\fruit\\";
	String[] modules = module.split("\\.");
	String modulePath="";
	String modelPath_="";
	int i=1;
	for (String v : modules){
		if (i==1){
			modelPath_ = modelPath_ + v;
			modulePath = modulePath + v;
		}
		else
		{
			modelPath_ = modelPath_ + "\\" + v;
			modulePath = modulePath + "/" + v;
		}
		i++;
	}
	System.out.println("====="+pckPath);
	pckPath = pckPath + modelPath_ + "\\";

	// 页面路径，放到WEB-INF下面是为了不让手动输入路径访问jsp页面，起到安全作用
	String webPath = srcPath + "WebRoot\\WEB-INF\\jsp\\" + module + "\\";

	// java,xml文件名称
	String modelPath = "model\\" + className + "Info.java";
	String beanPath = "dto\\"  + className + "Dto.java";
	String mapperPath = "mapper\\" +  className + "Mapper.java";
	String servicePath = "service\\" +  className + "Service.java";
	String controllerPath = "action\\" +  className + "Action.java";
	String sqlMapperPath = "mapper\\" +  className + "Mapper.xml";
	// jsp页面路径
	// String pageEditPath = lowerName+"\\"+lowerName+"Edit.jsp";
	// String pageListPath = lowerName+"\\"+lowerName+"List.jsp";

	VelocityContext context = new VelocityContext();
	context.put("projectName", projectName); //
	context.put("className", className); //
	context.put("lowerName", lowerName);
	context.put("codeName", codeName);
	context.put("tableName", tableName);
	context.put("module", module);
	context.put("username", username);
	context.put("currentDate", currentDate);
	context.put("modulePath", modulePath);

	/****************************** 生成bean字段 *********************************/
	try {
		context.put("feilds", createBean.getBeanFeilds(tableName)); // 生成bean
	} catch (Exception e) {
		e.printStackTrace();
	}
	/****************************** 生成bean字段 *********************************/
	try {
		context.put("toDto", createBean.getToDto(tableName)); // 生成bean
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	try {
		context.put("toModel", createBean.getToModel(tableName)); // 生成bean
	} catch (Exception e) {
		e.printStackTrace();
	}
	/******************************* 生成sql语句 **********************************/
	try {
		Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
		context.put("columnDatas", createBean.getColumnDatas(tableName)); // 生成bean
		context.put("SQL", sqlMap);
	} catch (Exception e) {
		e.printStackTrace();
		return;
	}

	//
	// -------------------生成文件代码---------------------/
	CommonPageParser.WriterPage(context, "TempBean.java", pckPath, beanPath); // 生成实体Bean
	CommonPageParser.WriterPage(context, "TempModel.java", pckPath, modelPath); // 生成Model
	CommonPageParser.WriterPage(context, "TempMapper.java", pckPath, mapperPath); // 生成MybatisMapper接口
																					// 相当于Dao
	CommonPageParser.WriterPage(context, "TempService.java", pckPath, servicePath);// 生成Service
	CommonPageParser.WriterPage(context, "TempMapper.xml", pckPath, sqlMapperPath);// 生成Mybatis
																					// xml配置文件
	CommonPageParser.WriterPage(context, "TempController.java", pckPath, controllerPath);// 生成Controller
																							// 相当于接口
	// 生JSP页面，如果不需要可以注释掉
//	context.put("basePath", "${e:basePath()}");
//	 CommonPageParser.WriterPage(context, "TempList.jsp",webPath,
//	 pageListPath);//生成Controller 相当于接口
	// CommonPageParser.WriterPage(context, "TempEdit.jsp",webPath,
	// pageEditPath);//生成Controller 相当于接口

	/************************* 修改xml文件 *****************************/
	// WolfXmlUtil xml=new WolfXmlUtil();
	// Map<String,String> attrMap=new HashMap<String, String>();
	// try{
	// /** 引入到mybatis-config.xml 配置文件 */
	// // attrMap.clear();
	// attrMap.put("resource","com/yiya/mybatis/"+className+"Mapper.xml");
	// xml.getAddNode(rootPath+"conf/mybatis-config.xml",
	// "/configuration/mappers", "mapper", attrMap, "");
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	
    }
    
    
//	public static void main(String[] args) throws IOException, SQLException {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
//		CreateBean createBean = new CreateBean();
//		createBean.setOracleInfo(url, username, passWord);
//		/** 此处修改成你的 表名 和 中文注释 ***/
//		// 模块名称
//		String projectName = "htta-system";//工程名：htta-system
//		String module = "system";// 模块包名：system
//		String username = "张卓伟"
//			+ "";// 开发人员名字
//		String currentDate = df.format(new Date());
//		String tableName = " UT_XT_RY"; //表名 UT_XT_RYCD
//		String codeName = "人员信息";// 中文注释
//		codeName = new String(codeName.getBytes(),"utf-8");
//		username = new String(username.getBytes(),"utf-8");
//		String className = createBean.getTablesNameToClassName(tableName);
//		String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
//		Map<String, String> tableMap = createBean.getTableNames();
//		if (tableMap.containsKey(tableName.toUpperCase())){
//			codeName = tableMap.get(tableName.toUpperCase());
//		}
//		// 根路径
//		String srcPath ="D:\\SourceCode\\Java_SVN\\htta-system\\src"; //本地代码路径：D:\\SourceCode\\Java_SVN\\htta-system\\src
//		// 包路径
//		String pckPath = srcPath + "\\main\\java\\com\\timesoft\\";
//		String[] modules = module.split("\\.");
//		String modulePath="";
//		String modelPath_="";
//		int i=1;
//		for (String v : modules){
//			if (i==1){
//				modelPath_ = modelPath_ + v;
//				modulePath = modulePath + v;
//			}
//			else
//			{
//				modelPath_ = modelPath_ + "\\" + v;
//				modulePath = modulePath + "/" + v;
//			}
//			i++;
//		}
//		pckPath = pckPath + modelPath_ + "\\";
//
//		// 页面路径，放到WEB-INF下面是为了不让手动输入路径访问jsp页面，起到安全作用
//		String webPath = srcPath + "WebRoot\\WEB-INF\\jsp\\" + module + "\\";
//
//		// java,xml文件名称
//		String modelPath = "model\\" + className + "Info.java";
//		String beanPath = "dto\\"  + className + "Dto.java";
//		String mapperPath = "mapper\\" +  className + "Mapper.java";
//		String servicePath = "service\\" +  className + "Service.java";
//		String controllerPath = "action\\" +  className + "Action.java";
//		String sqlMapperPath = "mapper\\" +  className + "Mapper.xml";
//		// jsp页面路径
//		// String pageEditPath = lowerName+"\\"+lowerName+"Edit.jsp";
//		// String pageListPath = lowerName+"\\"+lowerName+"List.jsp";
//
//		VelocityContext context = new VelocityContext();
//		context.put("projectName", projectName); //
//		context.put("className", className); //
//		context.put("lowerName", lowerName);
//		context.put("codeName", codeName);
//		context.put("tableName", tableName);
//		context.put("module", module);
//		context.put("username", username);
//		context.put("currentDate", currentDate);
//		context.put("modulePath", modulePath);
//
//		/****************************** 生成bean字段 *********************************/
//		try {
//			context.put("feilds", createBean.getBeanFeilds(tableName)); // 生成bean
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		/****************************** 生成bean字段 *********************************/
//		try {
//			context.put("toDto", createBean.getToDto(tableName)); // 生成bean
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			context.put("toModel", createBean.getToModel(tableName)); // 生成bean
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		/******************************* 生成sql语句 **********************************/
//		try {
//			Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
//			context.put("columnDatas", createBean.getColumnDatas(tableName)); // 生成bean
//			context.put("SQL", sqlMap);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//
//		//
//		// -------------------生成文件代码---------------------/
//		CommonPageParser.WriterPage(context, "TempBean.java", pckPath, beanPath); // 生成实体Bean
//		CommonPageParser.WriterPage(context, "TempModel.java", pckPath, modelPath); // 生成Model
//		CommonPageParser.WriterPage(context, "TempMapper.java", pckPath, mapperPath); // 生成MybatisMapper接口
//																						// 相当于Dao
//		CommonPageParser.WriterPage(context, "TempService.java", pckPath, servicePath);// 生成Service
//		CommonPageParser.WriterPage(context, "TempMapper.xml", pckPath, sqlMapperPath);// 生成Mybatis
//																						// xml配置文件
//		CommonPageParser.WriterPage(context, "TempController.java", pckPath, controllerPath);// 生成Controller
//																								// 相当于接口
//		// 生JSP页面，如果不需要可以注释掉
////		context.put("basePath", "${e:basePath()}");
////		 CommonPageParser.WriterPage(context, "TempList.jsp",webPath,
////		 pageListPath);//生成Controller 相当于接口
//		// CommonPageParser.WriterPage(context, "TempEdit.jsp",webPath,
//		// pageEditPath);//生成Controller 相当于接口
//
//		/************************* 修改xml文件 *****************************/
//		// WolfXmlUtil xml=new WolfXmlUtil();
//		// Map<String,String> attrMap=new HashMap<String, String>();
//		// try{
//		// /** 引入到mybatis-config.xml 配置文件 */
//		// // attrMap.clear();
//		// attrMap.put("resource","com/yiya/mybatis/"+className+"Mapper.xml");
//		// xml.getAddNode(rootPath+"conf/mybatis-config.xml",
//		// "/configuration/mappers", "mapper", attrMap, "");
//		// }catch(Exception e){
//		// e.printStackTrace();
//		// }
//	}

	/**
	 * 获取项目的路径
	 * 
	 * @return
	 */
	public static String getRootPath() {
		String rootPath = "";
		try {
			File file = new File(CommonPageParser.class.getResource("/").getFile());
			rootPath = file.getParentFile().getParentFile().getParent() + "\\";
			rootPath = java.net.URLDecoder.decode(rootPath, "utf-8");
			System.out.println(rootPath);
			return rootPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootPath;
	}
	
	private static String getInputPara() throws IOException {
		String module;
		BufferedReader strin2=new BufferedReader(new InputStreamReader(System.in));  
		module=strin2.readLine();
		return module;
	}
	
}
