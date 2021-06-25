package collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class CollectionDemo {
    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        });
        queue.add("1");
        queue.add("3");
        queue.add("2");
        String value;
        while ((value = queue.poll()) != null) {
            System.out.println(value);
        }
    }

    private static void testList() {
        ArrayList<Object> list = new ArrayList<>();
        Object object0 = new Object();
        list.add(object0);
        list.add(object0);

        list.forEach(o -> System.out.println(o.hashCode()));


        ListIterator<Object> listIterator = list.listIterator();

        LinkedList linkedList;


    }

    private static void testMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("", "");


        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.size();
        concurrentHashMap.get("");
        concurrentHashMap.put("", "");
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("", "");
        concurrentSkipListMap.get("");
    }

}
