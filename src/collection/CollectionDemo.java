package collection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class CollectionDemo {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.size();
        concurrentHashMap.get("");
        concurrentHashMap.put("","");
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("","");
        concurrentSkipListMap.get("");
    }
}
