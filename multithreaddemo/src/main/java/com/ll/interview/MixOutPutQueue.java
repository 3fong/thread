package com.ll.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * 连个线程交替打印: A1B2...Z26
 */
public class MixOutPutQueue {

    private static final String[] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static void main(String[] args) throws InterruptedException {
        TransferQueue queue = new LinkedTransferQueue();
        TransferQueue queue2 = new LinkedTransferQueue();
        Thread write;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                    System.out.print(LETTER[i]);
                    queue.add(1);
                    try {
                        queue2.take();
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
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue2.add(1);
                }
            }
        };
        Thread monitor = new Thread(runnable);

        write.start();
        monitor.start();

    }

}
