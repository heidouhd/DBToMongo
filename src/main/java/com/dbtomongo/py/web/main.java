package com.dbtomongo.py.web;

import com.dbtomongo.py.util.C3P0Inner;
import com.dbtomongo.py.util.DBUtil;
import com.dbtomongo.py.util.DepotsTableUtil;
import com.dbtomongo.py.util.MongoManager;
import com.dbtomongo.py.util.constant;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String args[]) {
        MongoManager mongoManager = new MongoManager(constant.HOST, constant.PORT);
        C3P0Inner c3P0Inner = new C3P0Inner(constant.driver, constant.jdbcURL, constant.username, constant.password);
        //获取对应的表名 库名
        List<String> list = DepotsTableUtil.getLibraryTable(constant.DBName, constant.DBtableName);
        for (int i = 0; i < list.size(); i++) {
            String table = list.get(i);
            System.out.println("查看库：" + table);
            String sql = jointSQL(table);
            String tables[] = table.split("[.]");
            constant.REALDBNAME = tables[0];
            constant.REALDBTABLENAME = tables[1];
            updateOrInsert(sql, mongoManager, c3P0Inner);
        }

    }

    private static void updateOrInsert(String sql, MongoManager mongoManager, C3P0Inner c3P0Inner) {

        List jsonArray = DBUtil.execute(sql, new ArrayList(), c3P0Inner);
//        System.out.println(jsonArray.toString());
        //获取主键
        String key = jsonArray.get(0).toString();
        //插入mongo
        for (int i = 1; i < jsonArray.size(); i++) {
            DBObject jsonObject = (DBObject) jsonArray.get(i);
            Object js = jsonObject.get(key);
            //// TODO: 2018/5/4 主键类型有待修改 
            Long value = (Long) js;
            System.out.println(value);
//          System.out.println(jsonObject.toString());
            mongoManager.updateData(jsonObject.toString(), key, value);
        }
    }

    private static String jointSQL(String table) {
        String sql = "select * from " + table;
        return sql;
    }


}
