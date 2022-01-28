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
public class Monitor1ErrorSleep {
    // 1 由于是引用数据类型 volatile 的添加其实并没有作用;它本身就是全局变量,不同线程具备可见性
    static List<Integer> container = new ArrayList<>();
    static String lock = new String();
    static String lock2 = new String();
    public static void main(String[] args) {
        thread();
    }

    /**
     * 当前版本无法准确的控制容器的计数,同时线程的抢占也无法控制.所以无法满足上面的要求
     */
    public static void thread(){
        Thread write;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i=0; i < 10 ; i++) {
                        // 2 ArrayList 通过 this.size = s + 1; 来计数,这种写法无法保证线程安全
                        container.add(i);
                        System.out.println("wirte size: " + i);
                        if(i==5){
                            try {
                                System.out.println("wirte sleep start");
                                // 3 线程进入阻塞状态,不释放锁,执行时间单位确定,可读性比Thread.sleep(1000) 好
                                TimeUnit.SECONDS.sleep(1);
                                System.out.println("wirte sleep end");
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
                synchronized (lock) {
                    System.out.println("minitor start");
                    while(true){
                        //
                        if(container.size()==5){
                            break;
                        }
                    }
                    System.out.println("minitor size: " + container.size());
                }
            }
        };
        Thread monitor = new Thread(runnable);

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println("minitor2 size: " + container.size());
                }
            }
        };
        Thread monitor2 = new Thread(runnable2);

        write.start();
        monitor.start();
        monitor2.start();
    }
}
