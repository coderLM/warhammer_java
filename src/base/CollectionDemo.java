package base;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class CollectionDemo {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.get(1);
        list.iterator().forEachRemaining(element->{
        });
        Map<Integer,Integer> map=new HashMap<>();

        Set<Integer> set =new HashSet<>();
    }

}
