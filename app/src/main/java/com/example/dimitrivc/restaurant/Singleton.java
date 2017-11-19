package com.example.dimitrivc.restaurant;

import java.util.ArrayList;

/**
 * Created by DimitrivC on 18-11-2017.
 */

public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private ArrayList<String> arrayList;

    private Singleton(){
        arrayList = new ArrayList<>();
    }

    public static Singleton getInstance() {

        return ourInstance;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}








