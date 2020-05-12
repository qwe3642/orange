package com.fruit.createsource;

/**
 * 表字段类
 * 
 * @author Administrator
 * 
 */
public class ColumnData {

	private String columnName;
	private String columnNameStr;
	private String dataType;
	private String columnComment;
	private int length;
	private String dataDefault;
	private String isPrimarykey = "false";
	private int scale;
	public int getScale() {
		return scale;
	}

	public String getColumnNameStr() {
		return columnNameStr;
	}

	public void setColumnNameStr(String columnNameStr) {
		this.columnNameStr = columnNameStr;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getIsPrimarykey() {
		return isPrimarykey;
	}

	public void setIsPrimarykey(String isPrimarykey) {
		this.isPrimarykey = isPrimarykey;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDataDefault() {
		return dataDefault;
	}

	public void setDataDefault(String dataDefault) {
		this.dataDefault = dataDefault;
	}

}
