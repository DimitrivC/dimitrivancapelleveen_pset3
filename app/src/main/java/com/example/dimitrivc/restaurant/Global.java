package com.example.dimitrivc.restaurant;

import android.app.Application;

import java.util.List;

/**
 * Created by DimitrivC on 18-11-2017.
 */


public class Global extends Application{
    private int data=200;

    public int getData(){
        return this.data;
    }

    public void setData(int d){
        this.data=d;
    }

//    private static Global instance;
//    private static List<String> GlobalList;
//
//    private Global(){}
//
//    public void setTest(List<String> G){
//        Global.GlobalList=G;
//    }
//
//    public List<String> getTest (){
//        return Global.GlobalList;
//    }
//
//    public static synchronized Global getInstance(){
//        if (instance==null){
//            instance=new Global();
//        }
//        return instance;
//    }

// EINDE CLASS
}
