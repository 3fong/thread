package com.ll.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * - 写一个固定容量同步容器,拥有put和get方法,以及getCount(),能支持2个
 * 生产者线程和10个消费者线程的阻塞调用
 *
 */
public class CreatorLock {
    private static List<String> container = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    private Condition pro = lock.newCondition();
    private Condition cons = lock.newCondition();



    public static void main(String[] args) {
        CreatorLock cl = new CreatorLock();
        // consumer
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    System.out.println(Thread.currentThread().getName()+" "+cl.get());
                }
            },"con"+ i ).start();
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

    public void put(String i) {
        try{
            lock.lock();
            // 保证多次执行,结果相同
            while(container.size() == 10){
                pro.await();
            }
            container.add(i);
            cons.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }

    public String get(){
        String remove = null;
        try {
            lock.lock();
            while (container.size() == 0) {
                cons.await();
            }
            remove = container.remove(container.size() - 1);
            pro.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return remove;
    }
}
