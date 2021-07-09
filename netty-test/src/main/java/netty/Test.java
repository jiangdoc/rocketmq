package netty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * @author jiangwenjie
 * @date 2021/4/30
 * @desc 顺序递增打印正整数，从1开始打印到100，中间换行分隔。不允许重复打印出相同的数字，比如打印结果里出现2个5，3个6之类的。
 *              要求如下： 1、使用三个线程A、B、C，其中线程A打印3的倍数，B打印5的倍数，C打印其他数字
 */
public class Test {
    private static Integer num = 1;

    public static void main(String[] args) {
        Semaphore semaphore =  new Semaphore(1);
        Thread thread3 = new Thread(() -> {
            while (true){
                if (num>100){
                    break;
                }

                if (num %3 ==0){
                    semaphore.acquireUninterruptibly();
                    System.out.println("thread3:"+num);
                    num++;
                }else{
                    semaphore.release();
                }
            }
        });

        Thread thread5 = new Thread(() -> {
            while (true){
                if (num>100){
                    break;
                }

                if (num %5 ==0 && num %3 !=0){
                    semaphore.acquireUninterruptibly();
                    System.out.println("thread5:"+num);
                    num++;
                }else{
                    semaphore.release();
                }
            }
        });

        Thread threadOther = new Thread(() -> {
            while (true){
                if (num>100){
                    break;
                }

                if (num %3 !=0 && num %5 !=0){
                    semaphore.acquireUninterruptibly();
                    System.out.println("threadOther:"+num);
                    num++;
                }else{
                    semaphore.release();
                }
            }
        });
        threadOther.start();
        thread3.start();
        thread5.start();
    }

}
