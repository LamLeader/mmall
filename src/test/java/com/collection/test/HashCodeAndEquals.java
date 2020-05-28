package com.collection.test;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/8 15:07
 * @version: 1
 * @modified By:
 */
public class HashCodeAndEquals {
      /**
       *https://mp.weixin.qq.com/s?__biz=MzIyNDU2ODA4OQ==&mid=2247483944&idx=1&sn=3a81af53aaaac56b60e50c1619433513&chksm=e80db45edf7a3d48807151107efd8d2b39f04424b6b1fe57212a92ea2b4927702a5250c37fa7&scene=21#wechat_redirect
       *1、【强制】关于hashCode和equals的处理，遵循如下规则：
       * 1）只要重写equals,就必须重新hashCode.
       * 2) 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的是对象必须
       * 重写这两个方法。
       * 3) 如果自定义对象为Map的键那么必须重写hashCode和equals
       * ps:String重写了hashCode和equal方法，所以我们可以非常愉快的使用String
       * 对象作为Key来使用
       * 原则
       * 1.同一个对象（没有发生过修改）无论何时调用hashCode()得到的返回值必须一样。
       * 如果一个key对象在put的时候调用hashCode()决定了存放的位置，而在get的时候调用hashCode()得到了不一样的返回值，这个值映射到了一个和原来不一样的地方，那么肯定就找不到原来那个键值对了。
       * 2.hashCode()的返回值相等的对象不一定相等，通过hashCode()和equals()必须能唯一确定一个对象。不相等的对象的hashCode()的结果可以相等。hashCode()在注意关注碰撞问题的时候，也要关注生成速度问题，完美hash不现实。
       * 3.一旦重写了equals()函数（重写equals的时候还要注意要满足自反性、对称性、传递性、一致性），就必须重写hashCode()函数。而且hashCode()的生成哈希值的依据应该是equals()中用来比较是否相等的字段。
       * 如果两个由equals()规定相等的对象生成的hashCode不等，对于hashMap来说，他们很可能分别映射到不同位置，没有调用equals()比较是否相等的机会，两个实际上相等的对象可能被插入不同位置，出现错误。其他一些基于哈希方法的集合类可能也会有这个问题
       */
   /**
    * @description
    * https://blog.csdn.net/bailu666666/article/details/81153815
    * 通过查看以上的源码，我们可以了解到：实际的逻辑都是在HashMap的put()方法中。
    * int hash = hash(key) 对传入的key计算hash值；
    * int i = indexFor(hash, table.length) 对hash值进行转换，转换成数组的index(HashMap中底层存储使用了Entry<K,V>[]数组)；
    * for (Entry<K,V> e = table[i]; e != null; e = e.next) 判断对应index下是否存在元素；
    * 如果存在，则if(e.hash == hash && ((k = e.key) == key || key.equals(k)))判断；
    * 如果不存在，则addEntry(hash, key, value, i)直接添加；
    * 在向HashMap中添加元素时，先判断key的hashCode值是否相同，如果相同，则调用equals()、==进行判断，若相同则覆盖原有元素；如果不同，则直接向Map中添加元素；
    * 因为hashCode()并不是完全可靠，有时候不同的对象他们生成的hashcode也会一样（生成hash值得公式可能存在的问题），
    * 所以hashCode()只能说是大部分时候可靠，并不是绝对可靠，所以我们可以得出：
    * 1.equal()相等的两个对象他们的hashCode()肯定相等，也就是用equal()对比是绝对可靠的。
    * 2.hashCode()相等的两个对象他们的equal()不一定相等，也就是hashCode()不是绝对可靠的。
    * @author 12484
    * @date 2020/1/8 15:08
    * @param
    * @return
    */

   /**
    * @description
    *  由于标识这个类的是他的内部的变量num和name, 所以我们就根据他们返回一个hash值，作为这个类的唯一hash值。
    *  所以如果我们的对象要想放进hashSet，并且发挥hashSet的特性（即不包含一样的对象），
    *  则我们就要重写我们类的hashCode()和equal()方法了。像String,Integer等这种类内部都已经重写了这两个方法。
    *  当然如果我们只是平时想对比两个对象 是否一致，则只重写一个equal()，然后利用equal()去对比也行的。
    * @author 12484
    * @date 2020/1/8 15:15
    * @param null
    * @return
    */
    class  Person{

        int num;
        String name;

        public int hashCode(){
            return num*name.hashCode();
       }


   }
    public static void main(String[] args) {

    }
}
