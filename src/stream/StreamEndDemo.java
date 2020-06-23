package stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 终端操作
 */
public class StreamEndDemo {
    public static void main(String[] args) {
        int run = -1;
        switch (run) {
            case -1:
                //测试执行顺序，说明Stream会挨个元素处理，除非"终端操作"对所需元素有要求；
                //例子中 max比较器需要至少两个元素，所以先执行了两个元素的filter0和filter1,再走到max
                testProgressMethod();
                break;
            case 0:
                //forEach 内部迭代
                forEachMethod();
                break;
            case 1:
                //收集
                collectMethod();
                break;
            case 2:
                //match相关：allMatch、anyMatch、noneMatch
                mathMethod();
                break;
            case 3:
                //find相关：findFirst、findAny
                findMethod();
                break;
            case 4:
                //count：计数
                break;
            case 5:
                //max、min：最值
                maxMinMethod();
                break;
            case 6:
                //reduce：归约
                reduceMethod();
                break;
        }
    }

    private static void testProgressMethod() {
        Stream.of(1, 2, 3, 4).filter(v -> {
            System.out.println("filter 0");
            return true;
        }).filter(v -> {
            System.out.println("filter 1");
            return true;
        }).max((a, b) -> {
            System.out.println("max");
            return a - b;
        });
        System.out.println("test end");
    }

    private static void forEachMethod() {
        Stream.of(1, 2, 3).forEach(System.out::println);
    }

    private static void collectMethod() {
        //一般使用Collectors静态方法 .toList()、.toMap()
        List<String> list = Stream.of(1, 2, 3).map(v -> (v + 1) + "").collect(Collectors.toList());
        System.out.println(list.size());

        Map<String, String> map = Stream.of(1, 2, 3).map(v -> (v + 1) + "").collect(Collectors.toMap(k -> k, v -> v));
        map.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
    }

    private static void mathMethod() {
        boolean allMatch = Stream.of(1, 2, 3, 4, 5).allMatch(v -> v < 5);
        boolean anyMatch = Stream.of(1, 2, 3, 4, 5).anyMatch(v -> v < 5);
        boolean noneMatch = Stream.of(1, 2, 3, 4, 5).noneMatch(v -> v < 5);
        System.out.println("allMatch:" + allMatch);
        System.out.println("anyMatch:" + anyMatch);
        System.out.println("noneMatch:" + noneMatch);
    }

    private static void findMethod() {
        Optional first = Stream.of(1, 2, 3).findFirst();
        if (first.isPresent()) {
            System.out.println("first:::" + first.get());
        }
        Optional any = Stream.of(1, 2, 3).findAny();
        if (any.isPresent()) {
            System.out.println("any:::" + any.get());
        }
    }

    private static void maxMinMethod() {
        Optional max = Stream.of(1, 2, 3).max(Comparator.comparingInt(a -> a));
        if (max.isPresent()) {
            System.out.println("max:::" + max.get());
        }
    }

    private static void reduceMethod() {
        int result = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println("reduce:::" + result);
    }

}
