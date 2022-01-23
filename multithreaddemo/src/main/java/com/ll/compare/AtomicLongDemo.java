package com.ll.compare;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <pre>
 * Purpose of the program: to demonstrate the poor performance of AtomicInteger and AtomicLong in high and low performance
 * AtomicLong is used in 16 threads.
 * Every time the value changes, it will be refreshed back to the main memory. When the competition is fierce, such flush and refresh operations consume a lot of resources, and CAS will often fail
 * </pre>
 * created at 2020/8/11 06:11
 * @author lerry
 */
public class AtomicLongDemo {
    /**
     * Number of threads in the thread pool
     */
    final static int POOL_SIZE = 1000;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        AtomicLong counter = new AtomicLong(0);
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

        ArrayList<Future> futures = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE * 100; i++) {
            futures.add(service.submit(new Task(counter)));
        }

        // Wait for all threads to finish executing
        for (Future future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        System.out.printf("The statistical results are:[%s]n", numberFormat.format(counter.get()));
        System.out.printf("Time consuming:[%d]millisecond", (System.currentTimeMillis() - start));
        // Close thread pool
        service.shutdown();
    }

    /**
     * There is an AtomicLong member variable that performs + 1 operations N times at a time
     */
    static class Task implements Runnable {

        private final AtomicLong counter;

        public Task(AtomicLong counter) {
            this.counter = counter;
        }

        /**
         * Each thread performs N + 1 operations
         */
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                counter.incrementAndGet();
            }
        }// end run
    }
}