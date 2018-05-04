package com.dbtomongo.py.util;

public class constant {

    public static String HOST = "deposlend.read.mongodb.dafy.db";// 端口
    public static int PORT = 40000;// 端口
//    public static String HOST = "127.0.0.1";// 端口
//    public static int PORT = 27017;// 端口
    public final static int POOLSIZE = 100;// 连接数量  
    public final static int BLOCKSIZE = 100; // 等待队列长度
    public static String MongoDBName = "DEPOSLEND";
    public static String MongoTableName = "tbMatchRelationForLender";

    public static String DBName = "DEPOS_MATCH";
    public static String DBtableName = "tbMatchRelationForLender";
    public static String driver = "com.mysql.jdbc.Driver";
    public static String jdbcURL = "jdbc:mysql://192.168.1.172:3306/"+DBName+"?Unicode=true&amp;characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    public static String username = "writedafy";
    public static String password = "writeDafy!@#$";
    public final static int MaxPoolSize = 40;
    public final static int MinPoolSize = 2;
    public final static int InitialPoolSize = 10;
    public final static int MaxStatements = 100;

    public static int  librarys = 20; //分库数量
    public static int tables = 100;//分表数量
    public static int  libraryStep = 1; //分库步长
    public static int tableStep = 1;//分表步长

    public static volatile String REALDBNAME = "";
    public static volatile String REALDBTABLENAME = "";


}
