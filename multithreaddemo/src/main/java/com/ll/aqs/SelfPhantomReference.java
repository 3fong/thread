package com.ll.aqs;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：**虚引用必须和引用队列 （ReferenceQueue）联合使用**。
 * 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
 * 你声明虚引用的时候是要传入一个queue的。当你的虚引用所引用的对象已经执行完finalize函数的时候，就会把对象加到queue里面。
 * 你可以通过判断queue里面是不是有对象来判断你的对象是不是要被回收了.
 */
public class SelfPhantomReference {
    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE = new ReferenceQueue<M>();

    public static void main(String[] args) throws InterruptedException {
        phantomReference();
    }

    /**
     * 虚引用回收测试 前置条件进行虚拟机参数配置 -Xms20m -Xmx20m
     *
     * 响应结果:
     * D:\workfile\jdk\jdk11.0.13\bin\java.exe -Xms20m -Xmx20m "-javaagent:D:\workfile\ide\IntelliJ IDEA 2021.2\lib\idea_rt.jar=64111:D:\workfile\ide\IntelliJ IDEA 2021.2\bin" -Dfile.encoding=UTF-8 -classpath D:\git_repo\demo\thread\multithreaddemo\target\classes com.ll.aqs.SelfPhantomReference
     * null
     * null
     * null
     * null
     * phantom is callbacked by jvm java.lang.ref.PhantomReference@3109723f
     * null
     * null
     * null
     * null
     * Exception in thread "Thread-0" java.lang.OutOfMemoryError: Java heap space
     * 	at java.base/java.util.LinkedList.linkLast(LinkedList.java:146)
     * 	at java.base/java.util.LinkedList.add(LinkedList.java:342)
     * 	at com.ll.aqs.SelfPhantomReference.lambda$phantomReference$0(SelfPhantomReference.java:23)
     * 	at com.ll.aqs.SelfPhantomReference$$Lambda$14/0x0000000100066840.run(Unknown Source)
     * 	at java.base/java.lang.Thread.run(Thread.java:834)
     *
     */
    public static void phantomReference(){
        PhantomReference<M> phantomReference = new PhantomReference(new M(),QUEUE);

        new Thread(() -> {
            while(true) {
                LIST.add(new byte[1024*1024]);//堆空间占用,用于触发gc
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * Returns this reference object's referent.
                 * Because the referent of a phantom reference is always inaccessible, this method always returns null.
                 * Returns: null
                 */
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()-> {
           while(true){
               /*
               垃圾回收时,会向队列中放入虚引用
               Reference queues, to which registered reference objects are appended
               by the garbage collector after the appropriate reachability changes are detected.
                */
               Reference<? extends M> poll = QUEUE.poll();
               if(poll!=null) {
                   System.out.println("phantom is callbacked by jvm "+ poll);
               }
           }
        }).start();

    }

    public static class M {

    }
}
