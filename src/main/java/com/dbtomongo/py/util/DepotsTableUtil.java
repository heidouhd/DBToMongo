package com.dbtomongo.py.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分库分表工具类
 * 用于获取对应的库和表1
 */
public class DepotsTableUtil {


    public static String getTable(String table,String suffix){

        return table + suffix;
    }

    public static String getLibrary(String library,String suffix){
        return library + suffix;
    }

    public static List<String> getLibraryTable(String library,String table){
        List<String> list = new ArrayList();
        if(constant.librarys<1 && constant.tables< 1){
            String libraryTable =  library+"."+table;
            list.add(libraryTable);
            return list;
        }
        for(int i = 0;i<constant.librarys;){
            String ls = i+"";
            String ts = "";
            if(i!=0){
                ts = i+"";
            }
            for(int j=0;j<constant.tables;){
                String js = "";
                if(i!=0&&j<10){
                    js = "0"+j;
                }else{
                    js = j+"";
                }
                String libraryTable =  getLibrary(library,ls+".") + getTable(table,ts+""+js+"");
                list.add(libraryTable);
                j+=constant.tableStep;
            }
            i+=constant.libraryStep;
        }
        return list;

    }


}
