package concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, 1);
        concurrentHashMap.put(2, 9);
        concurrentHashMap.put(3, 2);
        //set 可执行添加操作
        Set<Integer> set = concurrentHashMap.keySet(11);//mappedValue 默认值
        set.add(8);
        //-----更新值(具有原子性)----
        //替换：成功返回true，失败返回false，可循环替换，直至成功 do{...}while(replace code)
        concurrentHashMap.replace(1,1,6);
        //合并：有就用传入值，没有就merge传入值和旧值，不对key进行操作
        concurrentHashMap.merge(1, 2, Integer::sum);
        //操作：一般也不操作key
        concurrentHashMap.compute(1, (k, v) -> v + 1);
        //为了保证原子性，也可以将value改为 LongAdder

        //-----遍历相关----
        //Search
        int result = concurrentHashMap.search(1, (k, v) -> {
            System.out.println("key:" + k + " value:" + v);
            return k >= 2 ? v : null;
        });//不符合条件的要返回null，否则会结束搜索
        System.out.println("search:::" + result);
        //froEach 支持转换器--消费者
        concurrentHashMap.forEach(1, (k, v) -> k <= 1 ? null : "transformer:::" + k + ":" + v, System.out::println);
        //reduce 支持转换器--消费者
        System.out.println("reduceValues:::" + concurrentHashMap.reduceValues(1, Integer::sum));
        System.out.println("reduceValues v>=2:::" + concurrentHashMap.reduceValues(1, (v) -> v >= 2 ? v : null, Integer::sum));


//        HashMap<Integer, Integer> hashMap = new HashMap<>();
//        hashMap.put(null,null);
//        System.out.println( hashMap.containsKey(null));

//        Integer [] arr=new Integer[3];
//        System.out.println(arr[2]);
//        Hashtable<Integer,Integer> hashTable=new Hashtable<>();
//        hashTable.put(null,1);
    }
}
