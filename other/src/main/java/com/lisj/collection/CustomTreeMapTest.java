package com.lisj.collection;

import java.util.TreeMap;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-29 15:17
 */
public class CustomTreeMapTest {

    public static void main(String[ ] arg){
        CustomTreeMap<String,Integer> treeMap1 = new CustomTreeMap<>();
        treeMap1.put("1",1);
        treeMap1.put("2",2);
        treeMap1.put("3",3);
        treeMap1.put("4",4);
        treeMap1.put("5",5);
        treeMap1.put("6",6);
        treeMap1.put("7",7);
        //System.out.println(treeMap1);


        TreeMap<String,Integer> treeMap2 = new TreeMap<>();
        treeMap2.put("1",1);
        treeMap2.put("2",2);
        treeMap2.put("3",3);
        treeMap2.put("4",4);
        treeMap2.put("5",5);
        treeMap2.put("6",6);
        treeMap2.put("7",7);
        treeMap2.put("8",8);
        treeMap2.put("9",9);

        System.out.println(treeMap2);
        System.out.println(treeMap2.firstEntry());
    }
}
