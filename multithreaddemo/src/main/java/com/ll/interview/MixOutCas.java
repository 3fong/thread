package com.ll.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 连个线程交替打印: A1B2...Z26
 * CountDownLatch 无法实现此功能 因为无法明确的控制放行和阻塞线程
 */
public class MixOutCas {

    private static final String[] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    // static保证实例全局唯一;volatile保证线程内部不适用本地缓存,实现可见性
    static volatile int park=0;
    public static void main(String[] args) throws InterruptedException {

        Thread write;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                  while(park==1) {} // 循环阻塞
                  System.out.print(LETTER[i]);
                  park=park+1;
                }
            }
        };
        write = new Thread(runnable1);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                    while(park==0){}
                    System.out.println(i+1);
                    park=park-1;
                }
            }
        };
        Thread monitor = new Thread(runnable);

        write.start();
        monitor.start();
    }

}
