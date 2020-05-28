package com.collection.test;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description:
 * https://www.jianshu.com/p/d6cff3517688
 * https://www.cnblogs.com/wl0000-03/p/6019627.html
 * Set继承于Collection接口，是一个
 * 1、不允许出现重复元素，并且无序的集合，主要有HashSet和TreeSet两大实现类。
 * 在判断重复元素的时候，Set集合会调用hashCode()和equal()方法来实现。
 * HashSet是哈希表结构，主要利用HashMap的key来存储元素，计算插入元素的hashCode来获取元素在集合中的位置；
 * TreeSet是红黑树结构，每一个元素都是树中的一个节点，  插入的元素都会进行排序；
 * @author: 12484
 * @date: Created in 2020/1/7 11:06
 * @version: 1
 * @modified By:
 */
public class SetTest {

    /**
     * @description
     * TreeSet排序
     * https://www.cnblogs.com/wenbronk/p/7227400.html
     * @author 12484
     * @date 2020/1/8 17:21
     * @param
     * @return
     */
    private static Set<String> hashSet=null;
    private static Set<String> treeSet=null;

    /**------------------hashSet end-------------------**/
    /** 
     * @description
     * 但与List不同的是，Set还提供了equals(Object o)和hashCode()，供其子类重写，以实现对集合中插入重复元素的处理；
     * 使用Set需要重写 equals(Object o)和hashCode()方法
     * hashSet 特点
     * 不允许出现重复因素；
     * 允许插入Null值；
     * 元素无序（添加顺序和遍历顺序不一致）；
     * 线程不安全，若2个线程同时操作HashSet，必须通过代码实现同步；
     * HashSet有以下特点
     *   不能保证元素的排列顺序，顺序有可能发生变化
     *   不是同步的
     *   集合元素可以是null,但只能放入一个null
     * 当向HashSet结合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，
     * 然后根据 hashCode值来决定该对象在HashSet中存储位置。
     * 简单的说，HashSet集合判断两个元素相等的标准是两个对象通过equals方法比较相等，
     * 并且两个对象的hashCode()方法返回值相等
     * 注意，如果要把一个对象放入HashSet中，重写该对象对应类的equals方法，也应该重写其hashCode()方法。
     * 其规则是如果两个对象通过equals方法比较返回true时，其   hashCode也应该相同。另外，对象中用作equals比较标准的属性，都应该用来计算 hashCode的值。
     * @author 12484
     * @date 2020/1/8 11:47
     * @param str
     * @return 
     */
    public static void  operationHashSet(String str){
        String[] strArr=str.split(",");
        hashSet=new HashSet<>();
        for (String  strItem:strArr){
            hashSet.add(strItem);//添加
        }
        //删除
        hashSet.remove("7");
        for (String str1:hashSet){
            System.out.println("hashSet:"+str1);
        }

    }

    /**------------------hashSet begin-----------------**/

    /**------------------treeSet begin-----------------**/
    /**
     * @description
     * TreeSet类型是J2SE中唯一可实现自动排序的类型
     *     TreeSet是SortedSet接口的唯一实现类，TreeSet可以确保集合元素处于排序状态。TreeSet支持两种排序方式，自然排序 和定制排序，其中自然排序为默认的排序方式。向  TreeSet中加入的应该是同一个类的对象。
     *     TreeSet判断两个对象不相等的方式是两个对象通过equals方法返回false，或者通过CompareTo方法比较没有返回0
     * 自然排序
     *     自然排序使用要排序元素的CompareTo（Object obj）方法来比较元素之间大小关系，然后将元素按照升序排列。
     *     Java提供了一个Comparable接口，该接口里定义了一个compareTo(Object obj)方法，该方法返回一个整数值，实现了该接口的对象就可以比较大小。
     *     obj1.compareTo(obj2)方法如果返回0，则说明被比较的两个对象相等，如果返回一个正数，则表明obj1大于obj2，如果是 负数，则表明obj1小于obj2。
     *     如果我们将两个对象的equals方法总是返回true，则这两个对象的compareTo方法返回应该返回0
     * 定制排序
     *     自然排序是根据集合元素的大小，以升序排列，如果要定制排序，应该使用Comparator接口，实现 int compare(To1,To2)方法
     * @author 12484
     * @date 2020/1/8 17:19
     * @param str
     * @return
     */
    public static void operationTreeSet(String str){
        String [] strArr=str.split(",");
        treeSet=new TreeSet<>();
        for (String str1:strArr){
            treeSet.add(str1);
        }
        //treeSet.clear();//清除元素
        for (String  str2:treeSet){
            System.out.println("treeSet:"+str2);
        }

    }


    /**------------------treeSet end-----------------**/
    public static void main(String[] args) {
        String str1="5,6,7,1,2,3,4";
        String str2="1,2,3,4,5,6,7";
        SetTest.operationHashSet(str1);
        SetTest.operationTreeSet(str1);

    }


    /**
     * @description   总结
     * https://www.cnblogs.com/xiongH/p/7644037.html
     * list与Set、Map区别及适用场景
     * List,Set都是继承自Collection接口，Map则不是
     * List特点：元素有放入顺序，元素可重复, list支持for循环，也就是通过下标来遍历，也可以用迭代器
     * Set特点：元素无放入顺序，元素不可重复，重复元素会覆盖掉，
     * （注意：元素虽然无放入顺序，但是元素在set中的位置是有该元素的HashCode决定的，其位置其实是固定的，
     * 加入Set 的Object必须定义equals()方法 ，set只能用迭代，因为他无序，无法用下标来取得想要的值。）
     * Set和List对比：
     * a)       Set：检索元素效率低下，删除和插入效率高，插入和删除不会引起元素位置改变。时间复杂度永远为O(1)
     *
     * b)       List：和数组类似，List可以动态增长，查找元素效率高，插入删除元素效率低，因为会引起其他元素位置改变。
     *
     * Map适合储存键值对的数据
     * 线程安全集合类与非线程安全集合类 :
     * a)       LinkedList、ArrayList、HashSet是非线程安全的，Vector是线程安全的;
     *
     * b)       HashMap是非线程安全的，HashTable是线程安全的;
     *
     * c)        StringBuilder是非线程安全的，StringBuffer是线程安全的。
     *
     * List接口一共有三个实现类:ArrayList,LinkedList,Vector
     * a)       ArrayList是实现了基于动态数组的数据结构,因为地址连续，一旦数据存储好了，查询操作效率会比较高, 所以插入和删除操作效率比较低
     *
     * b)       LinkedList基于链表的数据结构,地址是任意的，所以在开辟内存空间的时候不需要等一个连续的地址，对于新增和删除操作add和remove，LinedList比较占优势。LinkedList 适用于要头尾操作或插入指定位置的场景
     *
     * c)        ArrayList和Vector都是用数组实现的，主要有这么三个区别：
     *
     *                      i.            Vector是多线程安全的
     *
     *                    ii.            Vector可以设置增长因子，而ArrayList不可以。
     *
     *                   iii.            Vector是一种老的动态数组，是线程同步的，效率很低，一般不赞成使用。
     *
     * Set的实现类:
     * a)       实现Set接口的重要类有HashSet（无序不重复），LinkedHashSet（按放入顺序有序不重复），TreeSet（按红黑树方式有序不重复），EnumSet，ConcurrentSkipListSet（来自于java.util.concurrent包），CopyOnWriteArraySet（来自于java.util.concurrent包）。
     *
     * b)       在Set接口中没有新增任何方法，所有方法均来自其父接口。它无法提供像List中按位存取的方法。在数学上一个集合有三个性质：确定性，互异性，无序性。
     *
     * c)        一般使用HashSet和TreeSet
     *
     *                      i.            适用场景分析:HashSet是基于Hash算法实现的，其性能通常都优于TreeSet。为快速查找而设计的Set，我们通常都应该使用HashSet，在我们需要排序的功能时，我们才使用TreeSet。
     *
     *                    ii.            TreeSet 是二差树（红黑树的树据结构）实现的,Treeset中的数据是自动排好序的，不允许放入null值
     *
     *                   iii.            HashSet 是哈希表实现的,HashSet中的数据是无序的，可以放入null，但只能放入一个null，两者中的值都不能重复，就如数据库中唯一约束
     *
     *                   iv.            HashSet要求放入的对象必须实现HashCode()方法，放入的对象，是以hashcode码作为标识的，而具有相同内容的String对象，hashcode是一样，所以放入的内容不能重复。但是同一个类的对象可以放入不同的实例
     *
     * Map的实现类中重要的HashMap与TreeMap、HashTable的区别及适用场景
     * a)       HashMap 非线程安全
     *
     * b)       HashMap：适用于Map中插入、删除和定位元素。
     *
     * c)        Treemap：适用于按自然顺序或自定义顺序遍历键(key)。
     *
     * d)       基于哈希表实现。使用HashMap要求添加的键类明确定义了hashCode()和equals()[可以重写hashCode()和equals()]，为了优化HashMap空间的使用，您可以调优初始容量和负载因子。
     *
     * e)       TreeMap：非线程安全基于红黑树实现。TreeMap没有调优选项，因为该树总处于平衡状态。
     * @author 12484
     * @date 2020/1/8 14:58
     * @param null
     * @return
     */

}
