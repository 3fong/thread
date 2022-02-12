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

- VarHandle 

    1 普通属性原子操作;    
    2 比反射快,直接操作二进制码

- ThreadLocal 线程独有    

    set方法,通过Thread.currentThread.map(ThreadLocal,obj)将值放到线程内部独享    
    声明式事务:通过aop进行事务的提前声明,避免业务代码侵入性.而事务的实现通过ThreadLocal获取线程独有变量信息    
    编程式事务:自己控制事务的开始,提交,回滚.优点:事务粒度灵活;缺点:代码侵入性高,开发和维护都不方便.

### 强软弱虚引用

WeakReference 只要垃圾回收,就会直接被回收;作用当强引用指向的引用消失后,弱引用就应该也消失;一般用在容器里???

PhantomReference 虚引用,jvm




作业
AQS addWork源码
WeakHashMap 作用



ide 虚拟机参数配置 -Xms20M -Xmx20M


参考资料:

[AbstractQueuedSynchronizer源码解读](https://www.cnblogs.com/micrari/p/6937995.html)

[AbstractQueuedSynchronizer 原理分析 - 独占/共享模式](https://cloud.tencent.com/developer/article/1113761)



