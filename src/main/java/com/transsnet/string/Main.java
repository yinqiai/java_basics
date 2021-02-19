package com.transsnet.string;

import java.util.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author yinqi
 * @date 2020/1/2
 */
public class Main {
    public static void main(String[] args) {


        List<String> listString0 = new ArrayList<>();
        listString0.add("xxx1");
        listString0.add("xxx2");
        listString0.add("xxx3");

        List<String> listString1 = new ArrayList<>(listString0);

        listString0.add("xxx4");

        listString0.set(0, "xxx1");
        listString0.set(1, "xxx3");
        listString0.set(2, "xxx3");


      /*  for(int i = 0, l = listString0.size(); i < l; i++)
        {
            System.out.println("listString0的第"+i+"个值为："+listString0.get(i));
            System.out.println("listString1的第"+i+"个值为："+listString1.get(i));
        }*/

        //System.out.println(isListEqual(listString0,listString1));

        Map m= new HashMap<String,String>();



        System.out.println("===================================");



       Map m2=new HashMap<String,String>();




        listString0.forEach(e->{

            m.put(e.toString(),"mark");

        });



        m2.putAll(m);

       // m.put("dsdsdsd","mark");

        Set<String> keys = m.keySet();
        for(String s : keys){
            System.out.println(s);
        }

        System.out.println("===================================");
        Set<String> keys1 = m2.keySet();
        for(String s : keys1){
            System.out.println(s);
        }
        System.out.println("===================================");

        System.out.println(isListEqual(keys,keys1));
    }

    /**
     * 比较两个List集合是否相等
     * <p>注：1. 如果一个List的引用为<code>null</code>，或者其包含的元素个数为0，那么该List在本逻辑处理中都算作空；
     * <p>2. 泛型参数E涉及到对象，所以需要确保正确实现了对应对象的<code>equal()</code>方法。
     * @param list1
     * @param list2
     * @return
     */
    public static <E>boolean isListEqual(Set<E> list1, Set<E> list2) {
        // 两个list引用相同（包括两者都为空指针的情况）
        if (list1 == list2) {
            return true;
        }

        // 两个list都为空（包括空指针、元素个数为0）
        if ((list1 == null && list2 != null && list2.size() == 0)
                || (list2 == null && list1 != null && list1.size() == 0)) {
            return true;
        }

        // 两个list元素个数不相同
        if (list1.size() != list2.size()) {
            return false;
        }

        // 两个list元素个数已经相同，再比较两者内容
        // 采用这种可以忽略list中的元素的顺序
        // 涉及到对象的比较是否相同时，确保实现了equals()方法
        if (!list1.containsAll(list2)) {
            return false;
        }

        return true;
    }
}
