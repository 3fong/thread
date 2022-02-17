package com.ll.container;

import java.util.concurrent.CompletableFuture;

/**
 * @author liulei
 * @Description
 * @create 2022/2/16 20:08
 */
public class SkipTableStudy {

    public static void main(String[] args) {

        CompletableFuture future = new CompletableFuture();

        Object t = new Object();
        Object tail = new Object();
        boolean eqs = t != (t = tail);
        boolean eqs2 = t != (t = tail);
        System.out.println(eqs);
        System.out.println(eqs2);
    }
}
