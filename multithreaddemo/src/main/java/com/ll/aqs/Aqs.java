package com.ll.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Aqs {

    public static void main(String[] args) {
        AbstractQueuedSynchronizer aqs = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) {
                System.out.println("tryAcquire: "+arg);
                return super.tryAcquire(arg);
            }
        };
        aqs.acquire(1);
    }
}
