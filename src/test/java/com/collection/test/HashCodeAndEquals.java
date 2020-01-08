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
