package com.ll.interview;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 连个线程交替打印: A1B2...Z26 最直接和正确的答案
 */
public class MixOutPutLockSupport {

    private static final String[] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    static Thread write,monitor = null;
    public static void main(String[] args) throws InterruptedException {
            Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                    System.out.print(LETTER[i]);
                    LockSupport.park();
                    LockSupport.unpark(monitor);
                }

            }
        };
        write = new Thread(runnable1);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < 26 ; i++) {
                    System.out.println(i+1);
                    LockSupport.unpark(write);
                    LockSupport.park();

                }
            }
        };
        monitor = new Thread(runnable);

        write.start();
        monitor.start();

    }

}
