package com.ll.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * - 写一个固定容量同步容器,拥有put和get方法,以及getCount(),能支持2个
 * 生产者线程和10个消费者线程的阻塞调用
 *
 */
public class CreatorSync {
    public static List<String> container = new ArrayList<>();


    public static void main(String[] args) {
        CreatorSync cl = new CreatorSync();
        // consumer
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    System.out.println(Thread.currentThread().getName()+" "+cl.get());
                }
            },"con"+ i ).start();
        }
        // 非必须, 用于划分线程范围
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // product
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 15; j++) {
                    cl.put(Thread.currentThread().getName()+"-"+j);
                }
            },"pro"+ i ).start();
        }

    }

    public synchronized void put(String i) {
        // 保证多次执行,结果相同
        while(container.size() == 10){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        container.add(i);
        notifyAll();
    }

    public synchronized String get(){
        while (container.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String remove = container.remove(container.size() - 1);
        notifyAll();
        return remove;
    }
}
