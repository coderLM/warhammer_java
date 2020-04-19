package design;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式
 * 目的：分离集合对象的遍历行为
 * 注：例子只为实现模式，细节不论（remove不作处理）
 */
public class IteratorDemo extends BaseDemo {
    @Override
    public void run() {
        RealAggregate aggregate=new RealAggregate();
       Iterator<Integer> iterator =  aggregate.createIterator();
       while(iterator.hasNext()){
           System.out.println(iterator.next());
       }
    }

    interface Iterator<T> {
        boolean hasNext();

        T next();

        void remove();
    }

    abstract class Aggregate {
        public abstract  Iterator createIterator();
    }

    class ListReverseIterator<T> implements Iterator<T> {
        List<T> list;
        int index;//当前脚标
        public ListReverseIterator(List<T> list) {
            this.list = list;
            index = list.size();
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            if (index > 0) {
                return list.get(--index);
            } else {
                return null;
            }
        }

        @Override
        public void remove() {
            if (index>=0&&index<list.size()) {
                list.remove(index);
            }
        }
    }

    class RealAggregate extends Aggregate {
        List<Integer> list = new ArrayList<>();
        public RealAggregate(){
            list.add(1);
            list.add(0);
            list.add(0);
            list.add(8);
            list.add(6);
        }
        @Override
        public Iterator<Integer> createIterator() {
            return new ListReverseIterator<>(list);
        }
    }
}
