## multi thread2

### 源码阅读

核心是理解别人的思路:    
1. 数据结构基础    
2. 设计模式    

原则:    
1. 跑不起来不读(debug跟踪,便于调用链识别及参数识别)
2. 解决问题就好-目的性
3. 一条线索到底(约束阅读范围)
4. 无关细节略过(约束阅读范围)
5. 一般不读静态(去除非必要内容)
6. 一般动态读法
7. 先读骨架

读骨架是识别类调用图,一般使用uml来记录和记忆:

![uml图](snapshot/uml.png)


### aqs AbstractQueuedSynchronizer

AQS的主要使用方式是继承它作为一个内部辅助类实现同步原语，它可以简化你的并发工具的内部实现，屏蔽同步状态管理、线程的排队、等待与唤醒等底层操作。

AQS设计基于模板方法模式，开发者需要继承同步器并且重写指定的方法，将其组合在并发组件的实现中，调用同步器的模板方法，模板方法会调用使用者重写的方法。

分类: 独占锁和共享锁

原理:CAS + volatile

state是volatile修饰的,并且设置state的方法处理有
有setState,还有compareAndSetState

入队,出队 cas

![分段锁](snapshot/aqs.png)

#### 实现

AQS内部维护一个CLH队列来管理锁。
线程会首先尝试获取锁，如果失败，则将当前线程以及等待状态等信息包成一个Node节点加到同步队列里。
接着会不断循环尝试获取锁（条件是当前节点为head的直接后继才会尝试）,如果失败则会阻塞自己，直至被唤醒；
而当持有锁的线程释放锁时，会唤醒队列中的后继线程。

下面列举JDK中几种常见使用了AQS的同步组件：

ReentrantLock: 使用了AQS的独占获取和释放,用state变量记录某个线程获取独占锁的次数,获取锁时+1，释放锁时-1，在获取时会校验线程是否可以获取锁。    
Semaphore: 使用了AQS的共享获取和释放，用state变量作为计数器，只有在大于0时允许线程进入。获取锁时-1，释放锁时+1。    
CountDownLatch: 使用了AQS的共享获取和释放，用state变量作为计数器，在初始化时指定。只要state还大于0，获取共享锁会因为失败而阻塞，直到计数器的值为0时，共享锁才允许获取，所有等待线程会被逐一唤醒。

- 核心功能

1 同步状态的管理    
2 线程的阻塞和唤醒    
3 同步队列的维护    

AQS独占锁的获取的流程示意如下:    
![AQS独占锁的获取的流程示意如下](https://images2015.cnblogs.com/blog/584724/201706/584724-20170612211300368-774544064.png)

### 变量原子性操作

实现变量原子性操作的方式:

1 使用AtomicInteger来达到这种效果，这种间接管理方式增加了空间开销，还会导致额外的并发问题；    
2 使用原子性的FieldUpdaters，由于利用了反射机制，操作开销也会更大；    
3 使用sun.misc.Unsafe提供的JVM内置函数API，虽然这种方式比较快，但它会损害安全性和可移植性，当然在实际开发中也很少会这么做。    
4 VarHandle

- VarHandle 

VarHandle 的出现替代了java.util.concurrent.atomic和sun.misc.Unsafe的部分操作。并且提供了一系列标准的**内存屏障**操作，用于更加细粒度的控制内存排序。在安全性、可用性、性能上都要优于现有的API。VarHandle 可以与任何字段、数组元素或静态变量关联，支持在不同访问模型下对这些类型变量的访问，包括简单的 read/write 访问，volatile 类型的 read/write 访问，和 CAS(compare-and-swap)等。


价值:    
    1 普通属性原子操作;    
    2 比反射快,直接操作二进制码

应用:

AQS在jdk1.9后,compareAndSetState通过VarHandle实现

```
 // VarHandle mechanics
    private static final VarHandle STATE;
    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            STATE = l.findVarHandle(AbstractQueuedSynchronizer.class, "state", int.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
        Class<?> ensureLoaded = LockSupport.class;
    }
    protected final boolean compareAndSetState(int expect, int update) {
        return STATE.compareAndSet(this, expect, update);
    }
```

[MethodHandles.Lookup](https://docs.oracle.com/javase/9/docs/api/java/lang/invoke/MethodHandles.Lookup.html)


### 强软弱虚引用

强引用: 不进行垃圾回收的引用.new的对象都是强引用.    
SoftReference: 当垃圾回收后,空间依旧不够会回收软引用占用的堆空间.用于缓存实现
WeakReference 只要垃圾回收,就会直接被回收;作用当强引用指向的引用消失后,弱引用就应该也消失;一般用在容器里???

PhantomReference 虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：**虚引用必须和引用队列 （ReferenceQueue）联合使用**。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之 关联的引用队列中。你声明虚引用的时候是要传入一个queue的。当你的虚引用所引用的对象已经执行完finalize函数的时候，就会把对象加到queue里面。你可以通过判断queue里面是不是有对象来判断你的对象是不是要被回收了.

强软弱虚引用回收时点:    
![强软弱虚引用](https://images2015.cnblogs.com/blog/647994/201702/647994-20170215235519441-1287012986.png)

PhantomReference结构:    
![PhantomReference struct](pic/phantom-struct.png)


引用示例:    
[demo](multithreaddemo/src/main/java/com/ll/aqs/SelfReference.java)

PhantomReference回收示例:    
[demo](multithreaddemo/src/main/java/com/ll/aqs/SelfPhantomReference.java)

- WeakHashMap

弱引用map通过Entry继承WeakReference来支持垃圾回收操作

```
private static class Entry<K,V> extends WeakReference<Object> implements Map.Entry<K,V> {}
```

demo:    
[demo](multithreaddemo/src/main/java/com/ll/aqs/SelfWeakHashMap.java)



- ThreadLocal 线程独有    

ThreadLocal.ThreadLocalMap结构:    
![ThreadLocal.ThreadLocalMap结构](pic/weakr-struct.png)

set方法,通过Thread.currentThread.map(ThreadLocal,obj)将值放到线程内部独享    
声明式事务:通过aop进行事务的提前声明,避免业务代码侵入性.而事务的实现通过ThreadLocal获取线程独有变量信息    
编程式事务:自己控制事务的开始,提交,回滚.优点:事务粒度灵活;缺点:代码侵入性高,开发和维护都不方便.


### 作业

AQS acquiry源码
WeakHashMap 作用:作缓存,key是弱引用,当key为null时,垃圾回收时会进行内存空间回收;



ide 虚拟机参数配置 -Xms20M -Xmx20M


参考资料:

[AbstractQueuedSynchronizer源码解读](https://www.cnblogs.com/micrari/p/6937995.html)

[AbstractQueuedSynchronizer 原理分析 - 独占/共享模式](https://cloud.tencent.com/developer/article/1113761)
[Java 9 变量句柄-VarHandle](https://www.jianshu.com/p/e231042a52dd)


