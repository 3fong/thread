package com.ll.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 连个线程交替打印: A1B2...Z26
 */
public class MixOutPut {

    private static final String[] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        Thread write;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
//                    latch.countDown();
                    System.out.print(LETTER[i]);
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        write = new Thread(runnable1);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                    System.out.println(i+1);
//                    latch.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread monitor = new Thread(runnable);

        write.start();
        monitor.start();

        for (int i = 0; i < 26; i++) {
            latch.countDown();
            latch2.countDown();
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
