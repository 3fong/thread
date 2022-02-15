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

CompletableFuture


### 设计思路学习

ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>

### 学习一下游戏开发

如何能创造别人需要的东西,最简单的就是游戏,创造规则,让别人去接受.实现资本积累.




### 参考资料

[Hashmap](https://blog.csdn.net/qq_37084904/article/details/109243886)

























