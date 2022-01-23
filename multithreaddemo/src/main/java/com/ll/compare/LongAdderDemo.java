package com.ll.compare;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 * Purpose of the program: To compare the performance with AtomicLong
 * </pre>
 * created at 2020/8/11 06:25
 * @author lerry
 */
public class LongAdderDemo {
    /**
     * Number of threads in the thread pool
     */
    final static int POOL_SIZE = 1000;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        LongAdder counter = new LongAdder();
        ExecutorService service = Executors.newFixedThreadPool(POOL_SIZE);

        ArrayList<Future> futures = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE * 100; i++) {
            futures.add(service.submit(new LongAdderDemo.Task(counter)));
        }

        // Wait for all threads to finish executing
        for (Future future : futures) {
            try {
                future.get();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        System.out.printf("The statistical results are:[%s]n", numberFormat.format(counter.sum()));
        System.out.printf("Time consuming:[%d]millisecond", (System.currentTimeMillis() - start));
        // Close thread pool
        service.shutdown();
    }

    /**
     * There is a LongAdder member variable, which performs + 1 operations N times at a time
     */
    static class Task implements Runnable {

        private final LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        /**
         * Each thread performs N + 1 operations
         */
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
        }// end run
    }// end class
}