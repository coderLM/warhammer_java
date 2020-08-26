package concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.DoubleBinaryOperator;

public class AtomicDemo {
    public static void main(String[] args) {

        DoubleAdder doubleAdder = new DoubleAdder();
        doubleAdder.add(3);
        doubleAdder.longValue();
        AtomicInteger atomicInteger=new AtomicInteger();
        atomicInteger.getAndAdd(1);
    }
}
