package com.dbtomongo.py.util;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import java.util.Collections;
import java.util.Iterator;

public class MongoManager {
    private static MongoClient mongo = null;

    public MongoManager(String host,int port) {
        // 其他参数根据实际情况进行添加
        try {
            ServerAddress serverAddress = new ServerAddress(host,port);
            MongoCredential credential = MongoCredential.createCredential("dafy", "DEPOSLEND", "dAfy$#@!2016".toCharArray());
            mongo = new MongoClient(serverAddress, Collections.singletonList(credential));
            MongoOptions opt = mongo.getMongoOptions();
            opt.connectionsPerHost = constant.POOLSIZE;
            opt.threadsAllowedToBlockForConnectionMultiplier = constant.BLOCKSIZE;
        } catch (MongoException e) {

        }
    }

    public static DB getDB(String dbName) {
        DB db =mongo.getDB(dbName);
        db.setWriteConcern(WriteConcern.JOURNALED);
        return db;
    }

    /**
     * 保存数据
     * @param data
     */
    public static void save(String data){
        DB myMongo = MongoManager.getDB("myMongo");
        DBCollection userCollection = myMongo.getCollection("user");
        DBObject dbo = (DBObject) JSON.parse(data);
        userCollection.insert(dbo);
    }

    /**
     * 更新“表”数据
     */
    public static void updateData(String json,String key,Long value){
        DB myMongo = MongoManager.getDB(constant.MongoDBName);
        DBObject updateCondition=new BasicDBObject();

        updateCondition.put(key,value);

        DBObject updatedValue=BasicDBObject.parse(json);

        DBObject updateSetValue=new BasicDBObject("$set",updatedValue);
        /**
         * update insert_test set headers=3 and legs=4 where name='fox'
         * updateCondition:更新条件
         * updateSetValue:设置的新值
         */
        DBCollection collection = myMongo.getCollection(constant.MongoTableName);
        WriteResult writeResult = collection.update(updateCondition, updateSetValue,true,true);
        System.out.println(json+"\n"+writeResult.toString());
    }

    /**
     * 返回查询结果集
     * @param collection
     * @return
     */
    private static DBCursor queryData(DBCollection collection){
        DBCursor queriedData=collection.find();
        return queriedData;

    }

    /**
     * 打印结果数据
     * @param description　结果数据相关描述
     * @param recordResult　结果集
     */
    private static void printData(String description,DBCursor recordResult){
        System.out.println(description);
        for(Iterator<DBObject> iter = recordResult.iterator(); iter.hasNext();){
            System.out.println(iter.next());
        }
    }

    /**
     * 更新“表”数据
     */
    public static void deleteData(String json,String key,String value){
        DB myMongo = MongoManager.getDB(constant.MongoDBName);
        DBObject updateCondition=new BasicDBObject();
        updateCondition.put("lId",6);
        DBCollection collection = myMongo.getCollection(constant.MongoTableName);
        collection.remove(updateCondition);
    }

}
