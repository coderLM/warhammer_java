package stream;

import java.util.*;
import java.util.stream.Stream;

/**
 * Stream 中间操作
 */
public class StreamMiddleDemo {
    public static void main(String[] args) {
        int run = 5;
        switch (run) {
            case 0:
                //排序
                sortedMethod();
                break;
            case 1:
                //去重
                distinctMethod();
                break;
            case 2:
                //过滤
                filterMethod();
                break;
            case 3:
                //截流+跳过
                limitSkipMethod();
                break;
            case 4:
                //map 每个元素进行操作
                mapMethod();
                break;
            case 5:
                //flatMap 每个元素转换为流，并连接成一条流
                flatMapMethod();
                break;
        }
    }

    private static void flatMapMethod() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<Integer>() {{
            this.add(1);
            this.add(2);
            this.add(3);
        }});
        list.add(new ArrayList<Integer>() {{
            this.add(4);
            this.add(5);
            this.add(6);
        }});
        list.stream().flatMap(Collection::stream).forEach(System.out::println);
    }

    private static void mapMethod() {
        Stream.of(1, 2, 3).map(v -> v * 2).forEach(System.out::println);
    }

    private static void limitSkipMethod() {
        //skip(n):跳过首部n位
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8).limit(7).skip(2).forEach(System.out::println);
    }

    private static void sortedMethod() {
//        Stream.generate(Math::random).limit(10).sorted().forEach(System.out::println);
        Stream.of(7, 1, 2, 3, 4, 4, 5, 6).limit(5).sorted((a, b) -> b - a).forEach(System.out::println);
    }

    private static void filterMethod() {
        long result = Stream.of(1, 2, 3, 4).filter(v -> v >= 3).count();
        System.out.println(result);

    }

    private static void distinctMethod() {
        List<User> list = new ArrayList<>();
        list.add(new User("fei", 10));
        list.add(new User("du", 20));
        list.add(new User("fei", 10));
        long result = list.stream().distinct().count();
        System.out.println(result);
    }

    private static class User {
        String name;
        int age;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        //ide 太省心... 自动生成
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
