package com.ll.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLock {



    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(i +"start---------------------------------------- ");
            latch(i);
            System.out.println(i +"end---------------------------------------- ");
        }
    }
    // 必须要保证
    public static void latch(int i) {
        ReentrantLock lock = new ReentrantLock();
        Condition monitor = lock.newCondition();
        Condition factory = lock.newCondition();
        // 这里需要是线程安全的类,实现操作原子性;同时多线程环境,减少静态变量的使用
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Thread factory = new Thread(() -> {
            System.out.println(i + "factory start");
            lock.lock();
            for (int num = 0; num < 10; num++) {
                list.add(num);
                System.out.println(i + "add " + num);

                if (list.size() == 5) {
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("factory await end");
                }
            }
            lock.unlock();
            System.out.println(i + "factory end");
        }, i + "factory");

        new Thread(() -> {
            System.out.println(i +"monitor start");
            try {
                factory.start();
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i +"monitor size: "+list.size());
            lock.notify();
            System.out.println("monitor release lock");
        },i +"monitor").start();


    }
}
