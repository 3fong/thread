package com.ll.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liulei
 * @Description 实现一个容器, 提供两个方法:add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数到5时,线程2给出提示并结束;
 * @create 2022/1/26 19:16
 */
public class Monitor2Error {

    static List<Integer> container = new ArrayList<>();
    private static String lock = new String();

    public static void main(String[] args) {
        thread();
    }

    /**
     * 当前版本可以一定程度上控制线程抢占;但是依旧无法精确控制线程的执行顺序,造成执行结果无法百分百准确
     */
    public static void thread(){
        Thread write;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    for (int i=0; i < 10 ; i++) {
                        container.add(i);
                        System.out.println("wirte size: " + i);
                        if(i==5){
                            lock.notify();
                        }
                    }
                }
            }
        };
        write = new Thread(runnable1);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("minitor start");
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("minitor size: " + container.size());
                }
            }
        };
        Thread monitor = new Thread(runnable);



        write.start();
        monitor.start();
    }
}
