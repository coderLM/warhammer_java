package base;

import java.util.*;

public class CollectionDemo2 {
    public static void main(String[] args) {
        Set<Integer> hashSet  = new HashSet<>();
        Set<Integer> linkedHashSet  = new LinkedHashSet<>();
        linkedHashSet.add(1);
        linkedHashSet.add(2);
        linkedHashSet.add(3);
        linkedHashSet.spliterator();
        List<Integer> list  =new ArrayList<>();
        Map<Integer,Integer> identityHashMap  = new IdentityHashMap<>();
        identityHashMap.put(1,1);
        System.out.println(identityHashMap.containsKey(new Integer(1)));
        Integer integer=1;
        Integer integer2=1;
        System.out.println(integer==integer2);
        Object obj = 1;
        Object obj2 = 2;
        System.out.println(obj==obj2);
        Stack<Integer> stack = new Stack<>();


    }
}
