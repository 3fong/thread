package com.ll.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liulei
 * @Description 实现一个容器, 提供两个方法:add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数到5时,线程2给出提示并结束;
 * @create 2022/1/26 19:16
 */
public class MonitorSupport4 {
    static List<Integer> container = new ArrayList<>();

    public static void main(String[] args) {
        thread();
    }

    public static void thread(){
        Thread write;
        Thread monitor;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("minitor start ");
                LockSupport.park();
                System.out.println("minitor size: " + container.size());
            }
        };
        monitor = new Thread(runnable);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 10 ; i++) {
                    container.add(i);
                    System.out.println("wirte size: " + i);
                    if(container.size()==5){
                        LockSupport.unpark(monitor);
                    }
                }
            }
        };

        write = new Thread(runnable1);
        monitor.start();
        write.start();
        try {
            write.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
