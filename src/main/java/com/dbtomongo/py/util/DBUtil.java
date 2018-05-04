package com.dbtomongo.py.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    private static PreparedStatement setStatement(Connection conn, String sql){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    private static PreparedStatement setParameter(PreparedStatement ps,Object...values){
        try {
            if(null != values){
                for(int i=1;i<=values.length;i++){
                    ps.setObject(i, values[i-1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static List execute(String sql,List parameters,C3P0Inner c3P0Inner){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List jsonArray = new ArrayList();
        try {
            conn = c3P0Inner.getConnection();
            ps = setStatement(conn, sql);
            ps = setParameter(ps, parameters.toArray());
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            ResultSet set = conn.getMetaData().getPrimaryKeys(constant.REALDBNAME,null,constant.REALDBTABLENAME);
            String pk_name = "";
            while (set.next()){
                pk_name = set.getString("COLUMN_NAME");
            }
            jsonArray.add(pk_name);
            while(rs.next()){
                DBObject jsonObject = new BasicDBObject();
                for( int i=1; i<=rsmd.getColumnCount(); i++ )
                {
                    String field = rsmd.getColumnName(i);
                    Integer type = rsmd.getColumnType(i); //5--DATA_TYPE int => SQL type from java.sql.Types

                   setObject(jsonObject,rs.getObject(i),field,type);
                }
                jsonArray.add(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
            c3P0Inner.realeaseResource(rs, ps, conn);
        }
        return jsonArray;
    }


    private static void setObject(DBObject jsonObject,Object object,String field,Integer mysqlType){
        if(object == null){
            return;
        }
        Class clazz = JDBCTypesUtils.jdbcTypeToJavaType(mysqlType);
        String name = clazz.getName();
        if("java.lang.Long".equals(name)){
            jsonObject.put(field,(Long)object);
        }else if("java.lang.Integer".equals(name)){
            jsonObject.put(field,(Integer)object);
        }else if("java.lang.Short".equals(name)){
            jsonObject.put(field,(Short)object);
        }else if("java.lang.Character".equals(name)){
            jsonObject.put(field,(Character) object);
        }else if("java.lang.Byte".equals(name)){
            jsonObject.put(field,(Byte)object);
        }else if("java.lang.Float".equals(name)){
            jsonObject.put(field,(Float)object);
        }else if("java.lang.Double".equals(name)){
            jsonObject.put(field,(Double)object);
        }else if("java.math.BigDecimal".equals(name)){
            jsonObject.put(field,(BigDecimal)object);
        }else if("java.lang.Boolean".equals(name)){
            jsonObject.put(field,(Boolean)object);
        }else{
            jsonObject.put(field,object+"");
        }
    }

}
