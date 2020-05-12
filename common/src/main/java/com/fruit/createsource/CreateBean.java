/*
 * 版    权:  Timesoft Copyright 2014-2016,All rights reserved
 * 文 件 名:  CreateBeana.java
 * 描    述:  <描述>
 * 修 改 人:  LUOXWPC
 * 修改时间:  2014-8-21
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.fruit.createsource;

import com.fruit.util.SystemCommonF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;


/**
 * <功能详细描述>
 * @author  LUOXWPC
 * @version  [版本号, 2014-8-21]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CreateBean {
	private Boolean bUseCamel = true; 
	@SuppressWarnings("unused")
	private Connection connection = SystemCommonF.setNullObj();
	static String url;
	static String username;
	static String password;
	static String rt = "\r\t";
	String SQLTables = "select * from UV_PT_TABLES t";
//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public Boolean getUseCamel() {
		return this.bUseCamel;
	}
	
	public void setUseCamel(Boolean bUseCamel) {
		this.bUseCamel = bUseCamel;
	}
	
	@SuppressWarnings("static-access")
	public void setOracleInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public List<String> getTables() throws SQLException {
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}
	
	public Map<String,String> getTableNames() throws SQLException {
		Map<String,String> results =  new HashMap<String, String>();
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String tableName = rs.getString(1);
			String tableDes = rs.getString(2);
			results.put(tableName, tableDes);
		}
		rs.close();
		ps.close();
		con.close();
		return results;
	}

	/**
	 * 查询表的字段，封装成List
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnData> getColumnDatas(String tableName)
			throws SQLException {
		String SQLColumns = "SELECT A.COLUMN_NAME,A.DATA_TYPE,A.DATA_DEFAULT,A.DATA_LENGTH,B.COMMENTS FROM USER_TAB_COLS A, USER_COL_COMMENTS B WHERE A.TABLE_NAME = '"
				+ tableName
				+ "' AND A.TABLE_NAME=B.TABLE_NAME AND A.COLUMN_NAME=B.COLUMN_NAME ORDER BY A.COLUMN_ID";
		SQLColumns = "select t.column_name,t.data_type,'<long>',t.data_precision,t.comments,t.isprimarykey,t.data_scale from UV_PT_TAB_COLUMNS t WHERE TABLE_NAME = '" + tableName + "' ORDER BY COLUMN_ID"; 
 
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List<ColumnData> columnList = new ArrayList<ColumnData>();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();
			String 	columnNameStr=name;
			
			if (bUseCamel) {
				String[] splitName = name.split("_");
				name = "";
				for (String s : splitName) {
					if ("".equals(name)) {
						name = s;
					} else {
						String temp = s.substring(0, 1).toUpperCase();
						temp += s.substring(1, s.length());
						name = name + temp;
					}
				}
			}
			
			String type = rs.getString(2);
			if ("text".equalsIgnoreCase(type)) {
				type = "varchar";
			}
			String dataDefault = rs.getString(3);
			int length = rs.getInt(4);
			String comment = rs.getString(5);
			String isprimarykey =rs.getString(6);
			int scale = rs.getInt(7);
			type = this.getType(type,length,scale);
			ColumnData cd = new ColumnData();
			cd.setColumnName(columnNameStr);
			cd.setColumnNameStr(name);
			cd.setDataType(type);
			cd.setColumnComment(comment);
			cd.setDataDefault(dataDefault);
			cd.setLength(length);
			cd.setIsPrimarykey(isprimarykey);
			cd.setScale(scale);
			columnList.add(cd);
		}
		argv = str.toString();
		method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	private String method;
	private String argv;

	/**
	 * 生成实体Bean 的属性和set,get方法
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getBeanFeilds(String tableName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			//String name = d.getColumnName();
			String name = d.getColumnNameStr();
			//bz  lrr lrsj xgr xgsj scbz yxbz flag
			if (!(name.equals("id") || name.equals("name") || name.equals("code")||name.equals("bz")||name.equals("lrr")
					||name.equals("lrsj")||name.equals("xgr")||name.equals("xgsj")||name.equals("scbz")||name.equals("yxbz")
					||name.equals("flag")) /*&& !d.getIsPrimarykey().equals("true")*/) {
				String type = d.getDataType();
				String comment = d.getColumnComment();
				// type=this.getType(type);
				String maxChar = name.substring(0, 1).toUpperCase();
				str.append(String.format("\r\t/**\r\t * %s\r\t */\r\t",comment)).append("private ").append(type + " ").append(name).append(";\r\t");
				String method = maxChar + name.substring(1, name.length());
				
				getset.append(String.format("\r\t/**\r\t * 返回 %s\r\t * @return %s\r\t */", comment, comment)).append("\r\tpublic ").append(type + " ").append("get" + method + "() {\r\t");
				getset.append("    return this.").append(name).append(";\r\t}\r\t");
				getset.append(String.format("\r\t/**\r\t * 对%s进行赋值\r\t * @param %s %s\r\t */",comment, name, comment)).append("\r\tpublic void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
				getset.append("    this." + name + "=").append(name).append(";\r\t}\r\t");
			}
		}
		argv = str.toString();
		method = getset.toString();
		return argv + method;
	}

	public String getToDto(String tableName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		//StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			//String name = d.getColumnName();
			String name = d.getColumnNameStr();
			if (!(name.equals("id") || name.equals("name") || name.equals("code")||name.equals("bz")||name.equals("lrr")
					||name.equals("lrsj")||name.equals("xgr")||name.equals("xgsj")||name.equals("scbz")||name.equals("yxbz")
					||name.equals("flag")) && !d.getIsPrimarykey().equals("true")) {
				//String type = d.getDataType();
				//String comment = d.getColumnComment();
				// type=this.getType(type);
				String maxChar = name.substring(0, 1).toUpperCase();
				str.append("\r\t\t").append("dto.set");
				String method = maxChar + name.substring(1, name.length());
				str.append(method).append("(this.get").append(method).append("());");
			}
		}
		argv = str.toString();
		
		return argv;
	}
	
	
	public String getToModel(String tableName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		//StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			//String name = d.getColumnName();
			String name = d.getColumnNameStr();
			if (!(name.equals("id") || name.equals("name") || name.equals("code")||name.equals("bz")||name.equals("lrr")
					||name.equals("lrsj")||name.equals("xgr")||name.equals("xgsj")||name.equals("scbz")||name.equals("yxbz")
					||name.equals("flag")) && !d.getIsPrimarykey().equals("true")) {
				//String type = d.getDataType();
				//String comment = d.getColumnComment();
				// type=this.getType(type);
				String maxChar = name.substring(0, 1).toUpperCase();
				str.append("\r\t\t").append("model.set");
				String method = maxChar + name.substring(1, name.length());
				str.append(method).append("(this.get").append(method).append("());");
			}
		}
		argv = str.toString();
		
		return argv ;
	}
	
	/**
	 * 
	 * <br>
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	// public List<Map<String,String>> getColumnsMap(String tableName) throws
	// SQLException{
	// String
	// SQLColumns="SELECT distinct COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM information_schema.columns WHERE  table_schema = 'ssi' and table_name =  '"+tableName+"' ";
	// // String SQLColumns="desc "+tableName;
	// Connection con=this.getConnection();
	// PreparedStatement ps=con.prepareStatement(SQLColumns);
	// List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
	// ResultSet rs=ps.executeQuery();
	// while(rs.next()){
	// Map<String,String> columnsMap=new HashMap<String, String>();
	// // String name = rs.getString(1);
	// // String type = rs.getString(2);
	// // String comment = rs.getString(3);
	//
	//
	// String name = rs.getString(1);
	// String type = rs.getString(2);
	// String comment = rs.getString(3);
	// type=this.getType(type);
	// columnsMap.put("COLUMN_NAME", name);
	// columnsMap.put("DATA_TYPE", type);
	// columnsMap.put("COLUMN_COMMENT", comment);
	// listMap.add(columnsMap);
	// }
	// rs.close();
	// ps.close();
	// con.close();
	// return listMap;
	// }
	public String getType(String type,int length,int scale) {
		type = type.toLowerCase();
		if ("char".equals(type) || "varchar".equals(type)
				|| "varchar2".equals(type)) {
			return "String";
		} else if ("int".equals(type) || "tinyint".equals(type)) {
			return "Integer";
		} else if ("number".equals(type) ||"decimal".equals(type)||"double".equals(type)) {
			if (scale >0){
				return "Double";
			}
			if (scale==0){
				if (length >10){
					return "Long";
				}else{
					return "Integer";
				}
			}
			return "Double";
		} else if ("bigint".equals(type)) {
			return "Long";
		} else if ("date".equals(type)) {
			return "Date";
		} else if ("timestamp".equals(type) || "date".equals(type)
				|| "datetime".equals(type) || "timestamp(6)".equals(type)){
			return "java.util.Date";
		} else if ("text".equals(type) || "longtext".equals(type)) {
			return "Text";
		}
		return null;
	}

	public void getPackage(int type, String createPath, String content,
			String packageName, String className, String extendsClassName,
			String... importName) throws Exception {
		if (null == packageName) {
			packageName = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; i++) {
			sb.append("import ").append(importName[i]).append(";\r");
		}
		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (null != extendsClassName) {
			sb.append(" extends ").append(extendsClassName);
		}
		if (type == 1) { // bean
			sb.append(" ").append("implements java.io.Serializable {\r");
		} else {
			sb.append(" {\r");
		}
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp += className.substring(1, className.length());
		if (type == 1) {
			sb.append("private " + className + " " + temp + "; // entity ");
		}
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		this.createFile(createPath, "", sb.toString());
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>表名转换成类名 每_首字母大写<br>
	 * <b>作者：</b><br>
	 * <b>日期：</b> 2011-12-21 <br>
	 * 
	 * @param tableName
	 * @return
	 */
	public String getTablesNameToClassName(String tableName) {
		String[] split = tableName.toLowerCase().split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < split.length; i++) {
				if (!split[i].equals("info")) {
					if (!split[i].equals("info")) {
						String tempTableName = split[i].substring(0, 1)
								.toUpperCase()
								+ split[i].substring(1, split[i].length());
						sb.append(tempTableName);
					}
				}
			}
			return sb.toString();
//			String className = sb.toString();
//			className = className.substring(0, 1).toUpperCase()
//					+ className.substring(1, className.length());

			//System.out.println(className);
			//return className;
		} else {
			String tempTables = split[0].substring(0, 1).toUpperCase()
					+ split[0].substring(1, split[0].length());
			return tempTables;
		}
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>创建文件<br>
	 * <b>作者：</b><br>
	 * <b>日期：</b> 2011-12-21 <br>
	 * 
	 * @param path
	 * @param fileName
	 * @param str
	 * @throws IOException
	 */
	public void createFile(String path, String fileName, String str)
			throws IOException {
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>生成sql语句<br>
	 * <b>作者：</b><br>
	 * <b>日期：</b> 2011-12-21 <br>
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	static String selectStr = "select ";
	static String from = " from ";

	public Map<String, Object> getAutoCreateSql(String tableName)
			throws Exception {
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		List<ColumnData> columnDatas = getColumnDatas(tableName);
		//Map<String, String> columnsMap = getColumnSplits(columnDatas);
		String columnstr = this.getColumnStrSplit(columnDatas);
		String columns = this.getColumnSplit(columnDatas);//columnsMap.get("fields");
		//String columns_pk = columnsMap.get("fields_pk");
		String[] columnList = getColumnList(columns); // 表所有字段
		String columnFields = getColumnFields(columns); // 表所有字段 按","隔开
		String insert = "insert into " + tableName + "\r\n\t\t  ("
				+ columns.replaceAll("\\|", ",") + ")\r\n\t\tvalues\r\n\t\t  (#{"
				+ columnstr.replaceAll("\\|", "},#{") + "})";
		String update = getUpdateSql(tableName, columnDatas);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		String batchInsert = getBatchInsert(tableName, columnDatas);
		String batchBatch = getBatchDelete(tableName,columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put(
				"insert",
				insert.replace("#{createTime}", "now()").replace(
						"#{updateTime}", "now()"));
		sqlMap.put(
				"update",
				update.replace("#{createTime}", "now()").replace(
						"#{updateTime}", "now()"));
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		sqlMap.put("batchInsert", batchInsert);
		sqlMap.put("batchBatch", batchBatch);
		return sqlMap;
	}

	/**
	 * 
	 * @Title: getBatchInsert
	 * @Description: 
	 * @param @param tableName
	 * @param @param columnsList
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public String getBatchInsert(String tableName, List<ColumnData> columnList) {
		StringBuffer sb = new StringBuffer();
		//sb.append("insert into " + tableName + "(");
		//for (int i = 0; i < columnsList.length; i++) {
		//	sb.append(columnsList[i]);
		//	sb.append(",");
		//}
		//sb.deleteCharAt(sb.length()-1);
		//sb.append(")\nvalues\n");
		//sb.append("		<foreach collection=\"list\" item=\"obj\" index=\"index\" separator=\",\" > \n (#{obj.id}");
		sb.append("#{obj.id}");
		for (int i = 1; i < columnList.size(); i++) {
			sb.append(",#{obj.");
			sb.append(columnList.get(i).getColumnNameStr());
			sb.append("}");
		}
		//sb.append(")</foreach>");
		return sb.toString();

	}

	public String getBatchDelete(String tableName,String[] columnsList) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from " + tableName + "\r\n\t\t where " + columnsList[0] + " in ");

		sb.append("\r\n\t\t<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">");
		sb.append("\r\n\t\t\t#{item}");
		sb.append("\r\n\t\t</foreach>");
		return sb.toString();

	}

	/**
	 * delete
	 * 
	 * @param tableName
	 * @param columnsList
	 * @return
	 * @throws SQLException
	 */
	public String getDeleteSql(String tableName, String[] columnsList)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("from ").append(tableName).append("\r\n\t\t where ");
		sb.append(columnsList[0]).append(" = #{").append("id")
				.append("}");
		return sb.toString();
	}

	/**
	 * 根据id查询
	 * 
	 * @param tableName
	 * @param columnsList
	 * @return
	 * @throws SQLException
	 */
	public String getSelectByIdSql(String tableName, String[] columnsList)
			throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("select <include refid=\"Base_Column_List\" />");
		sb.append("\r\n\t\t  from ").append(tableName).append("\r\n\t\t where ");
		sb.append(columnsList[0]).append(" = #{").append("id").append("}");
		return sb.toString();
	}

	/**
	 * 获取所有的字段，并按","分割
	 * 
	 * @param columns
	 * @return
	 * @throws SQLException
	 */
	public String getColumnFields(String columns) throws SQLException {
		String fields = columns;
		if (fields != null && !"".equals(fields)) {
			fields = fields.replaceAll("[|]", ",");
		}
		return fields;
	}

	/**
	 * 
	 * @param columns
	 * @return
	 * @throws SQLException
	 */
	public String[] getColumnList(String columns) throws SQLException {
		String[] columnList = columns.split("[|]");
		return columnList;
	}

	/**
	 * 修改记录
	 * 
	 * @param tableName
	 * @param columnsList
	 * @return
	 * @throws SQLException
	 */
	public String getUpdateSql(String tableName, List<ColumnData> columnList)
			throws SQLException {
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < columnList.size(); i++) {
			ColumnData data = columnList.get(i);
			if ("CREATETIME".equals(data.getColumnName().toUpperCase()))
				continue;

			if (sb.length() > 0) {
				sb.append("\r\n\t\t      ");
			}
			if ("UPDATETIME".equals(data.getColumnName().toUpperCase()))
				sb.append(data.getColumnName()  + "=now()");
			else
				sb.append(data.getColumnName()  + "=#{" + data.getColumnNameStr()  + "}");
			// 最后一个字段不需要","
			if ((i + 1) < columnList.size()) {
				sb.append(",");
			}
		}
		String update = "update " + tableName + "\r\n\t\t  set " + sb.toString()
				+ "\r\n\t\t where " + columnList.get(0).getColumnName() + "=#{id}";
		return update;
	}

	public String getUpdateSelectiveSql(String tableName,
			List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		ColumnData cd = columnList.get(0); // 获取第一条记录，主键
		sb.append("\r\n\t\t<trim  suffixOverrides=\",\">");
		for (int i = 1; i < columnList.size(); i++) {
			ColumnData data = columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\r\n\t\t\t<if test=\"").append(data.getColumnNameStr()).append(" != null");
			// String 还要判断是否为空
			if ("String" == data.getDataType()) {
				sb.append(" and ").append(data.getColumnNameStr()).append(" != ''");
			}
			sb.append("\">");
			sb.append("\r\n\t\t\t\t" + columnName + "=#{" + data.getColumnNameStr() + "}");
			// 最后一个字段不需要","
			if ((i + 1) < columnList.size()) {
				sb.append(",");
			}			
			sb.append("\r\n\t\t\t</if>");
		}
		sb.append("\r\n\t\t</trim>");
		String update = "update " + tableName + "\r\n\t\t  set " + sb.toString()
				+ "\r\n\t\t where " + cd.getColumnName() + "=#{" + cd.getColumnNameStr()+ "}";;
		return update;
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>获取所有列名字<br>
	 * <b>作者：</b><br>
	 * <b>日期：</b> 2011-12-21 <br>
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getColumnSplit(List<ColumnData> columnList)
			throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnData data : columnList) {
			commonColumns.append(data.getColumnName() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1,
				commonColumns.length()).toString();
	}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>获取所有列名字<br>
	 * <b>作者：</b><br>
	 * <b>日期：</b> 2011-12-21 <br>
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getColumnSplits(List<ColumnData> columnList)
			throws SQLException {
		StringBuffer fields = new StringBuffer();
		StringBuffer fields_pk = new StringBuffer();
		Map<String, String> strings = new HashMap<String, String>();
		for (ColumnData data : columnList) {
			fields.append(data.getColumnName() + "|");
			if (data.getIsPrimarykey().equals("true")){
				fields_pk.append("id|");
			}else{
				fields_pk.append(data.getColumnName() + "|");
			}
		}
		strings.put("fields", fields.delete(fields.length() - 1,fields.length()).toString());
		strings.put("fields_pk", fields_pk.delete(fields_pk.length() - 1,fields_pk.length()).toString());
		return strings;
	}
	
	public String getColumnStrSplit(List<ColumnData> columnList)
			throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnData data : columnList) {
			commonColumns.append(data.getColumnNameStr() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1,
				commonColumns.length()).toString();
	}
}
