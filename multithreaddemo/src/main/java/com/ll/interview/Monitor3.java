package com.ll.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liulei
 * @Description 实现一个容器, 提供两个方法:add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数到5时,线程2给出提示并结束;
 * @create 2022/1/26 19:16
 */
public class Monitor3 {

    static List<Integer> container = new ArrayList<>();
    private static String lock = new String();

    public static void main(String[] args) {
        thread();
    }

    /**
     * 当前版本可以一定程度上控制线程抢占;但是依旧无法精确控制线程的执行顺序,造成执行结果无法百分百准确
     * wait,notify是成对出现的,所以要保证monitor线程要先执行,才能触发在thread notify时唤醒monitor线程
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
                        if(container.size()==5){
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                        System.out.println("minitor start write thread");
                        write.start();
                        System.out.println("minitor wait");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("minitor size: " + container.size());
                    lock.notify();
                }
            }
        };
        Thread monitor = new Thread(runnable);

        monitor.start();
    }
}
