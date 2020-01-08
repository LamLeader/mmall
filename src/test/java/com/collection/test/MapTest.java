package com.collection.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * Map是无序的，它的存储结构是哈希表<key,value>键值对，
 * map中插入元素是根据key计算出的哈希值来存储元素的，
 * 因此他不是按照元素的添加顺序来存储对象的，所以Map是无序的。它的实现类有：
 * 1、HashMap；
 * 2、concurrentHashMap
 * 2、HashTable；
 * 3、TreeMap；
 * @author: 12484
 * @date: Created in 2020/1/7 11:05
 * @version: 1
 * @modified By:
 */
public class MapTest {

    /**
     * @description  map的初始值为16 大于16需要扩容
     * @author 12484
     * @date 2020/1/7 18:34
     * @param null
     * @return
     */
    private static Map<String,Object> hashMap=null;
    private static Map<String,Object> hashTable=null;
    private static Map<String,Object> treeMap=null;
    private static Map<String,Object> concurrentHashMap=null;

    /**------------------hashMap begin-------------------**/
    /**
     * @description  为map添加key 遍历map中的所有key value
     * TreeMap和HashMap的区别 https://blog.csdn.net/qq_41672180/article/details/97122462
     * Map：在数组中是通过数组下标来对 其内容进行索引的，而Map是通过对象来对 对象进行索引的，用来 索引的对象叫键key，其对应的对象叫值value；
     * 1、HashMap是通过hashcode()对其内容进行快速查找的；HashMap中的元素是没有顺序的；--无序
     *       TreeMap中所有的元素都是有某一固定顺序的，如果需要得到一个有序的结果，就应该使用TreeMap；
     * 2、HashMap和TreeMap都不是线程安全的（意味着程序中没有synchronized关键字性能优异）；--线程不安全
     * 3、HashMap继承AbstractMap类；覆盖了hashcode() 和equals() 方法，以确保两个相等的映射返回相同的哈希值；
     *      TreeMap继承SortedMap类；他保持键的有序顺序；
     * 4、HashMap：基于hash表实现的；使用HashMap要求添加的键类明确定义了hashcode() 和equals() （可以重写该方法）；为了优化HashMap的空间使用，可以调优初始容量和负载因子；
     *      TreeMap：基于红黑树实现的；TreeMap就没有调优选项，因为红黑树总是处于平衡的状态；
     * 5、HashMap：适用于Map插入，删除，定位元素；
     *      TreeMap：适用于按自然顺序或自定义顺序遍历键（key）；
     * @author 12484
     * @date 2020/1/7 17:42
     * @param
     * @return
     */
    public static void operationForHashMap(){
        hashMap=new HashMap<String,Object>();
        hashMap.put("str1",123);
        hashMap.put("str2",123);
        hashMap.put("","");
        for (String strKey:hashMap.keySet()){
            System.out.println("key:"+strKey+",value:"+hashMap.get(strKey));
        }
    }
    /**------------------hashMap end-------------------**/

    /**------------------ConcurrentHashMap begin-------------------**/
    /** 
     * @description  HashMap？ConcurrentHashMap？相信看完这篇没人能难住你！ https://blog.csdn.net/weixin_44460333/article/details/86770169
     * https://blog.csdn.net/qq_30683329/article/details/80455779
     * 如果你经常参加面试，一定会被问到这个map实现类，这个map实现类是在jdk1.5中加入的，其在jdk1.6/1.7中的主要实现原理是segment段锁，
     * 它不再使用和HashTable一样的synchronize一样的关键字对整个方法进行枷锁，而是转而利用segment段落锁来对其进行加锁，以保证Map的多线程安全。
     * 其实可以理解为，一个ConcurrentHashMap是由多个HashTable组成，所以它允许获取不用段锁的线程同时持有该资源，segment有多少个，
     * 理论上就可以同时有多少个线程来持有它这个资源。
     * 其默认的segment是一个数组，默认长度为16。也就是说理论商可以提高16倍的性能。
     * 但是要注意咯，在JAVA的jdk1.8中则对ConcurrentHashMap又再次进行了大的修改，取消了segment段锁字段，采用了CAS+Synchronize技术来保障线程安全。
     * 底层采用数组+链表+红黑树的存储结构，也就是和HashMap一样。这里注意Node其实就是保存一个键值对的最基本的对象。
     * 其中Value和next都是使用的volatile关键字进行了修饰，以确保线程安全。这里推荐一下大神的Volatile的深入理解篇，
     * 写的非常好http://www.cnblogs.com/xrq730/p/7048693.html
     * 在插入元素时，会首先进行CAS判定，如果OK就插入其中，并将size+1，但是如果失败了，就会通过自旋锁自旋后再次尝试插入，直到成功。
     * 所谓的CAS也就是compare And Swap，即在更改前先对内存中的变量值和你指定的那个变量值进行比较，
     * 如果相同这说明在这期间没有被修改过，则可以进行修改，而如果不一样了，则就要停止修改，否则就会覆盖掉其他的参数。即内存值a，旧值b，
     * 和要修改的值c，如果这里a=b，那么就可以进行更新，就可以将内存值a修改成c。否则就要终止该更新操作。
     * 为什么这里会用volatile进行修饰，我在其他博客找到了答案。主要有两个用处：
     * 1、令这个被修饰的变量的更新具有可见性，一旦该变量遭到了修改，其他线程立马就会知道，立马放弃自己在自己工作
     * 内存中持有的该变量值，转而重新去主内存中获取到最新的该变量值。
     * 2、产生了内存屏障，这里volatile可以保证CPU在执行代码时保证，所有被volatile中被修饰的之前的一定在之前被执行，
     * 也就是所谓的“指令重排序”。同hashMap一样，在JDK1.8中，如果链表中存储的Entry超过了8个则就会自动转换链表为红黑树，提高查询效率。
     * https://www.jianshu.com/p/5dbaa6707017
     * ConcurrentHashMap是Java中的一个线程安全且高效的HashMap实现。平时涉及高并发如果要用map结构，那第一时间想到的就是它
     * 那么我就这几个方面了解一下ConcurrentHashMap：
     * 1）ConcurrentHashMap在JDK8里结构
     * 2）ConcurrentHashMap的put方法、szie方法等
     * 3）ConcurrentHashMap的扩容
     * 4）HashMap、Hashtable、ConccurentHashMap三者的区别
     * 5）ConcurrentHashMap在JDK7和JDK8的区别
     * @author 12484
     * @date 2020/1/7 18:33
     * @param
     * @return 
     */
    public static void operationForConcurrentHashMap(){
        concurrentHashMap=new ConcurrentHashMap<String, Object>();
        concurrentHashMap.put("str",123);
        concurrentHashMap.put("qwer",234);
        for (String strKey:concurrentHashMap.keySet()){
            System.out.println("key:"+strKey+",value:"+concurrentHashMap.get(strKey));
        }
    }

    /**------------------ConcurrentHashMap end-------------------**/

    /**------------------HashTable begin-------------------**/
    /**
     * @description  HashMap和HashTable的区别
     * https://www.cnblogs.com/williamjie/p/9099141.html
     * 三、HashTable和HashMap区别
     *       1、继承的父类不同
     *       Hashtable继承自Dictionary类，而HashMap继承自AbstractMap类。但二者都实现了Map接口。
     *       2、线程安全性不同
     *       javadoc中关于hashmap的一段描述如下：此实现不是同步的。如果多个线程同时访问一个哈希映射，而其中至少一个线程从结构上修改了该映射，则它必须保持外部同步。
     *       Hashtable 中的方法是Synchronize的，而HashMap中的方法在缺省情况下是非Synchronize的。在多线程并发的环境下，可以直接使用Hashtable，不需要自己为它的方法实现同步，但使用HashMap时就必须要自己增加同步处理。（结构上的修改是指添加或删除一个或多个映射关系的任何操作；仅改变与实例已经包含的键关联的值不是结构上的修改。）这一般通过对自然封装该映射的对象进行同步操作来完成。如果不存在这样的对象，则应该使用 Collections.synchronizedMap 方法来“包装”该映射。最好在创建时完成这一操作，以防止对映射进行意外的非同步访问，如下所示：
     *       Map m = Collections.synchronizedMap(new HashMap(...));
     *       Hashtable 线程安全很好理解，因为它每个方法中都加入了Synchronize。这里我们分析一下HashMap为什么是线程不安全的：
     * @author 12484
     * @date 2020/1/7 18:19
     * @param
     * @return
     */
    public static void operationForHashTable(){
        hashTable=new Hashtable<String,Object>();
        hashTable.put("str1",1111);
        hashTable.put("str2",1111);
        hashTable.put("",1111);
        for (String strKey:hashTable.keySet()){
            System.out.println("key:"+strKey+",value:"+hashTable.get(strKey));
        }
    }
    /**------------------HashTable end-------------------**/

    /**------------------treeMap begin-------------------**/
    /**
     * @description  TreeMap和HashMap的区别
     * https://blog.csdn.net/qq_41672180/article/details/97122462
     * @author 12484
     * @date 2020/1/7 18:25
     * @param
     * @return
     */
    public static void operationForTreeMap(){
        treeMap=new TreeMap<String, Object>();
        treeMap.put("str",124);
        treeMap.put("str",345);
        for (String strKey:treeMap.keySet()){
            System.out.println("key:"+strKey+",value:"+treeMap.get(strKey));
        }
    }

    /**------------------treeMap end-------------------**/
    /**
     * @description  测试主方法
     * @author 12484
     * @date 2020/1/7 17:43
     * @param args
     * @return
     */
    public static void main(String[] args) {
        //MapTest.operationForHashMap();
        //MapTest.operationForHashTable();
        operationForTreeMap();
    }
}
