package com.fruit.util;

import com.fruit.createsource.CreateJava;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreateTemplateCode {
    public static void main(String[] args) throws IOException, SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("url", "jdbc:mysql://127.0.0.1:3306/fruit?useUnicode=true&useSSL=true&verifyServerCertificate=false&characterEncoding=utf-8&serverTimezone=GMT");// 数据库链接地址
        map.put("dbusername", "root");// 数据库用户名
        map.put("dbpassWord", "root");// 数据库密码
        map.put("projectName", "admin-user");// 工程名：htta-system
        map.put("module", "adminuser");// 模块包名：system
        map.put("username", "zhangx");// 创建者
        map.put("tableName", "test1");// 表名
        map.put("codeName", "测试");// 表中文名

        CreateJava createJava = new CreateJava();
        try {
            createJava.createSource(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
