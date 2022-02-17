package com.ll.aqs;

//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.VarHandle;

/**
 * java 9 版本 VarHandle 的出现替代了java.util.concurrent.atomic和sun.misc.Unsafe的部分操作。
 * 并且提供了一系列标准的内存屏障操作，用于更加细粒度的控制内存排序。在安全性、可用性、性能上都要优于现有的API。
 * VarHandle 可以与任何字段、数组元素或静态变量关联，支持在不同访问模型下对这些类型变量的访问，
 * 包括简单的 read/write 访问，volatile 类型的 read/write 访问，和 CAS(compare-and-swap)等。
 *
 *     1 普通属性原子操作;
 *     2 比反射快,直接操作二进制码
 */

public class VarHandleStudy {
//    private int x=0;
//    private static VarHandle handle;
//    static {
//        try {
//            handle = MethodHandles.lookup().findVarHandle(VarHandleStudy.class,"x",int.class);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        VarHandleStudy study = new VarHandleStudy();
//
//        System.out.println((int)handle.get(study));
//        handle.set(study,9);
//        System.out.println(study.x);
//
//        handle.compareAndSet(study,9,10);
//        System.out.println(study.x);
//
//        handle.getAndAdd(study,10);
//        System.out.println(study.x);
//    }
}
