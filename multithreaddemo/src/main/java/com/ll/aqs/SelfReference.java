package com.ll.aqs;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SelfReference {
    public static void main(String[] args) throws InterruptedException {
        weakReference();
    }

    /**
     * 用于内存缓存 前置操作需要配置虚拟机内存参数 -Xms20M -Xmx20M
     * 运行结果:
     * [B@43a25848
     * [B@43a25848
     * null  // 软引用被垃圾
     */
    public static void softReference() throws InterruptedException {
        // 分配10m内存空间
        SoftReference<byte[]> s = new SoftReference(new byte[1024*1024*10]);
        System.out.println(s.get());
        System.gc();
        Thread.sleep(500);
        System.out.println(s.get());

        // 再分配15m内存空间,heap这时就装不下了,会触发垃圾回收.先进行正常垃圾回收,如果空间还不够会回收软引用占用的空间
        byte[] bytes = new byte[1024 * 1024 * 15];
        System.out.println(s.get());
    }

    /**
     * 虚引用 只要有垃圾回收,虚引用就会被回收
     * @throws InterruptedException
     */
    public static void weakReference() throws InterruptedException {
        // 分配10m内存空间
        WeakReference<byte[]> s = new WeakReference(new byte[1024*1024*10]);
        System.out.println(s.get());
        System.gc();
        Thread.sleep(500);
        System.out.println(s.get());

        // ThreadLocal底层就是使用的虚引用 ThreadLocalMap(Thread.threadLocals).Entry extends WeakReference
        ThreadLocal<byte[]> tl = new ThreadLocal<>();
        tl.set(new byte[]{});
        System.out.println(tl.get());
        tl.remove();
        System.out.println(tl.get());
    }

}
