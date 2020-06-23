package stream;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import test_util.TimeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Stream创建
 * 参考文章：https://segmentfault.com/a/1190000019855034
 */
public class StreamCreateDemo {
    public static void main(String[] args) {
        int run = 3;
        switch (run) {
            case 0:
                //集合创建
                TimeUtil.countFn(StreamCreateDemo::collectionCreate);
                break;
            case 1:   //数组创建
                arrayCreate();
                break;
            case 2:   //值创建
                valueCreate();
                break;
            case 3:   //函数创建(一定要用limit停止)
                fnCreate();
                break;

        }
    }

    private static void collectionCreate() {
        int len = 10000 * 1000 * 3;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(new Double(Math.random() * len).intValue());
        }
        long result = list.stream().filter(v -> v % 3 == 0).count();
        //并行
//        long result = list.parallelStream().filter(v -> v % 3 == 0).count();
        System.out.println("result:::" + result);
    }

    private static void arrayCreate() {
        int len = 100;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = i;
        }
        Arrays.stream(arr).count();
    }

    private static void valueCreate() {
        Stream.of(1, 2, 3).count();
    }

    private static void fnCreate() {
//        Stream.iterate(1, i -> (i * 2) + 1).limit(10).forEach(System.out::println);

        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }
}
