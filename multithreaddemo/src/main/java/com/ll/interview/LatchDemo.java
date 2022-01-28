package com.ll.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LatchDemo {

    // 这里需要是线程安全的类,实现操作原子性
    static List<Integer> list = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        latch(latch);
        latch2(latch);
        latch(latch);
        latch(latch);
        System.out.println("end");
        latch.countDown();
    }
    // 必须要保证
    public static void latch(CountDownLatch latch) {
        new Thread(() -> {
            System.out.println("monitor start");
            try {
                latch.await();
                System.out.println("monitor size: " + list.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "monitor").start();
    }

    public static void latch2(CountDownLatch latch) {
        new Thread(() -> {
            System.out.println("factory start");
            list.add(1);
            latch.countDown();
            System.out.println("factory end");
        },"factory").start();
    }
}
