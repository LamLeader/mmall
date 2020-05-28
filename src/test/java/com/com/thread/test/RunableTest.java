package com.com.thread.test;

/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/15 14:58
 * @version: 1
 * @modified By:
 */
public class RunableTest  implements  Runnable{
    @Override
    public void run() {
       int total= RunableTest.count(100);

    }

    public  static  int count(Integer num){
        int total=0;
        for (int i=0;i<num;i++){
            total+=total+i;
            System.out.println("total:"+total);
        }

        return total;
    }

    public static void main(String[] args) {

        Runnable runnable= new RunableTest();
        Thread thread=new Thread(runnable,"xiaowu");
        thread.start();



    }
}
