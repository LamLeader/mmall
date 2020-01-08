package com.collection.test;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @description: list是按照元素的添加顺序来存储对象的，因此是有序且可重复的。
 * 他的实现类ArrayList、LinkedList、Vector都是有序的。
 * 初始值 10 大于10需要扩容
 * @author: 12484
 * @date: Created in 2020/1/7 11:05
 * @version: 1
 * @modified By:
 */
public class ListTest {
    private static List<String> arrList=null;
    private static List<String> linkedList=null;
    private static List<String> vector=null;

    /**------------------arrList begin-------------------**/
    /**
     * @description
     * ArrayList是我们在java开发过程中最常见的一种List实现类，
     * 属于线程不安全，读取速度快的一种List实现类。也是java入门时最常用的实现类。
     * 其中最重要的三个参数即如下代码所示，初始数组增量和一个数组
     * @author 12484
     * @date 2020/1/8 10:32
     * @param item
     * @return
     */
     public static void foreachArrList(String item){
        String [] str=null;
        arrList=new ArrayList<String>();
        str=item.split(",");
        for (String str1:str){
            arrList.add(str1);
        }
        arrList.remove(0);
        for (String str2:arrList){
            System.out.println("ArrList："+str2);
        }
    }
    /**------------------arrList begin-------------------**/


    /**------------------Vector begin-------------------**/
    /**
     * @description
     * 和ArrayList基本相似，利用数组及扩容实现List，
     * 但Vector是一种线程安全的List结构，它的读写效率不如ArrayList，其原因是在该实现类内在方法上加上了同步关键字，如
     * @author 12484
     * @date 2020/1/8 10:41
     * @param item
     * @return
     */
    public static void foreachVector(String item){
       String [] str=null;
       vector=new Vector<>();
       str=item.split(",");
       for (String str1:str){
           vector.add(str1);
       }
       for (String string:vector){
           System.out.println("vector:"+string);
       }
   }

    /**------------------Vector end-------------------**/


    /**------------------LinkedList begin-------------------**/
    /**
     * @description
     * LinkedList是利用内部类Node为数据单元的双向链表，
     * 同样LinkedList是线程不安全的，
     * 其具有读效率低，写效率高，操作效率高等特性，
     * 适合用于频繁add,remove等操作的List,同时可以节省一定的内存，在clear的情况下推荐使用GC回收，
     * 并且没有最大长度限制。可以看出双向链表的节点操作没有扩充的拷贝操作，在这种情况下操作
     * 相对于反复扩容效率要高，但也仅是相对的，但是有大量数据操作，特别是删除等，只需要做节点的横向移动，效率是很高的。
     * @author 12484
     * @date 2020/1/8 10:36
     * @param item
     * @return
     */
     public static void foreachLinkedList(String item){
        String [] str=null;
        linkedList=new LinkedList<>();
        str=item.split(",");
        for (String str1:str){
            linkedList.add(str1);
        }
        for (String str2:linkedList){
            System.out.println("LinkedList："+str2);
        }
    }


    /**------------------LinkedList end-------------------**/

     public static void main(String[] args) {
        String strItem="1,2,3,4,5,6,7,8";
        //ListTest.foreachArrList(strItem);
        ListTest.foreachLinkedList(strItem);
        ListTest.foreachVector(strItem);

    }
}
