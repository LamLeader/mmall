/**
 * @description:
 * @author: 12484
 * @date: Created in 2020/1/7 10:15
 * @version: 1
 * @modified By:
 */
public class RecursionTest {
    /**
     * @description  递归计算 100的阶乘
     * @author 12484
     * @date 2020/1/7 10:32
     * @param  initNum, sum
     * @return
     */
    public static void recursionCount(int initNum,int sum){
        sum=sum+initNum;
        if (initNum==100){
            System.out.println("====sum0=====:"+sum);
            return;
        }
        initNum++;
        recursionCount(initNum,sum);//递归体
        System.out.println("====sum1====:"+sum);
    }

    public static void main(String[] args) {
        RecursionTest.recursionCount(1,0);
    }



}
