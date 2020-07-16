package base;



import java.util.*;

public class CollectionDemo {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);

        bitSet.clear(1);
        System.out.println(bitSet.toString());
        System.out.println(bitSet.get(0));
    }
}
