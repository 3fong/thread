## 容器

结构概览:    
![容器结构概览](snapshot/container-system.png)

数据结构:物理结构 数组 链表

逻辑结构 树 队列

课程目录:    
![课程目录](snapshot/container-catalog.png)
collection:

list,set:装东西;queue:高并发;

### ConcurrentHashMap

Hashtable: hash表实现(数组+单向链表);线程安全;不支持null key,null值;初始容量为11;它在所有方法上都加了synchronized关键字,但是锁粒度太粗,虽然实现简单,但是写效率较差;
HashMap: hash表实现;线程不安全;支持null key,null值;初始容量是16;

HashMap导图:    
![HashMap map](https://img-blog.csdnimg.cn/img_convert/e944faeb9385f4fae5d2ee816138a7d1.png)
```
JDK1.8之前的HashMap由数组+链表组成的，数组是HashMap的主体，链表则是主要为了节解决哈希碰撞(两个对象调用的hashCode方法计算的哈希码值一致导致计算的数组索引值相同)而存在的（“拉链法”解决冲突）。

JDK1.8之后在解决哈希冲突时有了较大的变化，当链表长度大于阈值（或者红黑树的边界值，默认为8）并且当前数组的长度大于64时，此时此索引位置上的所有数据改为使用红黑树存储。

数组里面都是key-value的实例，在JDK1.8之前叫做Entry，在JDK1.8之后叫做Node。
```
HashMap 红黑树:    
![HashMap 红黑树](https://img-blog.csdnimg.cn/img_convert/af740fc6151848fec7712457ef55ef7b.png)

ConcurrentHashMap: 
ConcurrentHashMap.put实现核心步骤:
```
如果没有初始化就先调用initTable（）方法来进行初始化过程
如果没有hash冲突就直接CAS插入
如果还在进行扩容操作就先进行扩容
如果存在hash冲突，就加锁来保证线程安全，这里有两种情况，一种是链表形式就直接遍历到尾端插入，一种是红黑树就按照红黑树结构插入，
最后一个如果Hash冲突时会形成Node链表，在链表长度超过8，Node数组超过64时会将链表结构转换为红黑树的结构，break再一次进入循环
如果添加成功就调用addCount（）方法统计size，并且检查是否需要扩容

```



跳表结构:    
![跳表结构](snapshot/container-skiptable.png)



异步请求CompletableFuture

[CompletableFuture](https://zhuanlan.zhihu.com/p/344431341)

ConcurrentLinkedQueue:    
改示例的数据有错误,但是推导过程还是有参考意义的    
[ConcurrentLinkedQueue详解](https://www.jianshu.com/p/231caf90f30b)

CopyOnWriteArrayList: 写时复制列表,用于实现遍历的线程安全;写效率因为加锁,效率差;读的效率与其他结构差不多;而其他的共享存储对象的并发结构,核心是关心更新操作的线程安全,而不保证遍历的线程安全,会出现脏读的现象;

- 阻塞队列

![阻塞队列](https://upload-images.jianshu.io/upload_images/13587608-3fb24c186d396e82.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

LinkedBlockingQueue: 线程安全的排他队列结构;是一个**单向链表**实现的阻塞队列。该队列按 FIFO（先进先出）排序元素，新元素插入到队列的尾部，并且队列获取操作会获得位于队列头部的元素。链接队列的吞吐量通常要高于基于数组的队列，但是在大多数并发应用程序中，其可预知的性能要低。        
[LinkedBlockingQueue](https://www.jianshu.com/p/9394b257fdde)

PriorityQueue: 是一种无界的，线程不安全的数据有序队列;通过数组实现的，并拥有优先级的队列;存储的元素要求必须是可比较的对象， 如果不是就必须明确指定比较器


TransferQueue: 阻塞放入队列.应用场景: 要求多线程间实现同步请求并响应结果.
SynchronusQueue: SynchronousQueue是BlockingQueue的一种，所以SynchronousQueue是线程安全的。SynchronousQueue和其他的BlockingQueue不同的是SynchronousQueue的capacity是0。即SynchronousQueue不存储任何元素。也就是说SynchronousQueue的每一次insert操作，必须等待其他线性的remove操作。而每一个remove操作也必须等待其他线程的insert操作。这种特性可以让我们想起了Exchanger。和Exchanger不同的是，使用SynchronousQueue可以在两个线程中传递同一个对象。一个线程放对象，另外一个线程取对象。

ArrayBlockingQueue: 阻塞队列,底层数据结构为数组.

TransferQueue: 通过FIFO队列实现的公平线程进程队列.

DelayQueue: 延迟队列,给定时间内才能获取队列元素的功能.底层是通过PriorityQueue实现.

[阻塞队列介绍](https://www.itzhai.com/articles/graphical-blocking-queue.html)

#### 设计思路学习

ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>




### 连接池

ForkJoinPool: Java提供了ForkJoinPool来支持将一个任务拆分成多个“小任务”并行计算，再把多个“小任务”的结果合成总的计算结果.工作窃取（work-stealing）算法是指某个线程从其他队列里窃取任务来执行。    












### 学习一下游戏开发

如何能创造别人需要的东西,最简单的就是游戏,创造规则,让别人去接受.实现资本积累.




### 参考资料

[Hashmap](https://blog.csdn.net/qq_37084904/article/details/109243886)

[跳表和ConcurrentHashMap](https://blog.csdn.net/sunxianghuang/article/details/52221913)    



[题库](http://www.educity.cn/java/498061.html)






















