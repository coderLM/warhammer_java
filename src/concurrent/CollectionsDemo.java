package concurrent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsDemo {
    public static void main(String[] args) {

        List<Integer> synchronizedList= Collections.synchronizedList(new ArrayList<>());
        synchronizedList.size();
    }
}
