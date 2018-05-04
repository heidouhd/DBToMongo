package com.dbtomongo.py.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3P0Inner {
    private static ComboPooledDataSource ds;

    public  C3P0Inner(String driver,String jdbcURL,String username,String password){
        try {
            ds = new ComboPooledDataSource();//创建连接池实例

            ds.setDriverClass(driver);//设置连接池连接数据库所需的驱动

            ds.setJdbcUrl(jdbcURL);//设置连接数据库的URL

            ds.setUser(username);//设置连接数据库的用户名

            ds.setPassword(password);//设置连接数据库的密码

            ds.setMaxPoolSize(constant.MaxPoolSize);//设置连接池的最大连接数

            ds.setMinPoolSize(constant.MinPoolSize);//设置连接池的最小连接数

            ds.setInitialPoolSize(constant.InitialPoolSize);//设置连接池的初始连接数

            ds.setMaxStatements(constant.MaxStatements);//设置连接池的缓存Statement的最大数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取与指定数据库的连接
    public  ComboPooledDataSource getInstance(){
        return ds;
    }

    //从连接池返回一个连接
    public  Connection getConnection(){
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //释放资源
    public  void realeaseResource(ResultSet rs, PreparedStatement ps, Connection conn){
        if(null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(null != ps){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
