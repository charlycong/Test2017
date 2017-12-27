package com.lisj.defaultmethod;

/**
 * @author etix-2017-3
 * @version 1.0
 * @Date 2017-12-21 17:54
 */
public class TestDefaultMethodInherit {
}

class E extends D{
    public static void test(){
        hello();
    }
}

class D {
    private static String str = "ssss";

    public static void hello() {
        System.out.println(str);
    }
}

class C implements A, B {
    @Override
    public void hello() {

    }

    public static void tt(){
       // st();
    }

}


interface A {
    default void hello() {
        System.out.println("hello A");
    }

    public static void st() {

    }
}

interface B {
    default void hello() {
        System.out.println("hello B");
    }
}