package com.ll.container;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author liulei
 * @Description
 * @create 2022/2/16 20:08
 */
public class SkipTableStudy {

    public static void main(String[] args) {

        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        queue.offer("d");
        System.out.println(queue.poll());
        System.out.println(queue.poll());


    }
}
