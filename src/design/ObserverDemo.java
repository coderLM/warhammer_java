package design;

import java.util.*;

/**
 * 观察者模式
 * 目的：对象的一对多通知（解耦）
 */
public class ObserverDemo extends BaseDemo {
    @Override
    public void run() {
        BuildContext context = new BuildContext();
        //绑定
        Observer observer = EventCenter.attach(context, EventA.class,
                (Observer<EventA>) event -> System.out.println("get message event content:" + event.getContent()));
        //发送事件
        EventCenter.fire(new EventA("i am a message"));
        //解绑
//        boolean detachResult = EventCenter.detach(EventA.class, observer);
//        System.out.println("detach result:" + detachResult);
        //销毁上下文测试
        context.setActive(false);
        //发送事件
        EventCenter.fire(new EventA("i am a message too"));
    }
}

class BuildContext {
    public BuildContext() {
        this.active = true;
    }

    private boolean active;

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}

interface Event {
}

interface Observer<E extends Event> {
    void onUpgrade(E event);
}

class EventA implements Event {
    private String content;

    public EventA(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class EventCenter {
    private static final Map<Class, List<Observer>> classObsMap = new HashMap<>();
    private static final Map<BuildContext, List<Observer>> contextObsMap = new HashMap<>();
    private static final Object lock = new Object();

    // return 方便得到observer引用，取消订阅时使用
    public static Observer attach(BuildContext context, Class eventClass, Observer observer) {
        synchronized (lock) {
            dealMap(true, classObsMap, eventClass, observer);
            dealMap(true, contextObsMap, context, observer);
            return observer;
        }
    }

    // return 方便判断是否detach成功
    public static boolean detach(Class eventClass, Observer observer) {
        synchronized (lock) {
            for (Map.Entry<BuildContext, List<Observer>> entry : contextObsMap.entrySet()) {
                entry.getValue().removeIf(o-> o==observer);
            }
            return dealMap(false, classObsMap, eventClass, observer);
        }
    }

    public static void fire(Event event) {
        synchronized (lock) {
            List<Observer> badObservers = new ArrayList<>();
            Iterator<Map.Entry<BuildContext, List<Observer>>> contextIterator = contextObsMap.entrySet().iterator();
            while (contextIterator.hasNext()) {
                Map.Entry<BuildContext, List<Observer>> entry = contextIterator.next();
                if (null == entry.getKey() || !entry.getKey().isActive()) {
                    badObservers.addAll(entry.getValue());
                    contextIterator.remove();
                }
            }
            if (classObsMap.get(event.getClass()) != null) {
                for (Observer observer : classObsMap.get(event.getClass())) {
                    if (!badObservers.contains(observer)) {
                        observer.onUpgrade(event);
                    }
                }
            }
            //去除不再活跃的观察者
            if (badObservers.size() != 0) {
                for (Map.Entry<Class, List<Observer>> entry : classObsMap.entrySet()) {
                    //书写优化：匿名内部类-->lambda-->::

                    //形态0 正常版 完整的匿名内部类
//                    entry.getValue().removeIf(new Predicate<Observer>() {
//                        @Override
//                        public boolean test(Observer o) {
//                            return badObservers.contains(o);
//                        }
//                    });

                    //形态1 lambda版 干掉了类名和方法名，只保留参数和方法体
//                    entry.getValue().removeIf(o -> badObservers.contains(o));

                    //形态2 最终形态 去掉参数，方法体用 :: 表示
                    entry.getValue().removeIf(badObservers::contains);
                }
            }
        }
    }

    public static <P extends Object> boolean dealMap(boolean add, Map<P, List<Observer>> map, P key, Observer observer) {
        if (map.get(key) == null) {
            if (add) {
                map.put(key, new ArrayList<Observer>() {{
                    add(observer);
                }});
            }
        } else {
            if (add) {
                map.get(key).add(observer);
            } else {
                return map.get(key).remove(observer);
            }
        }
        return false;
    }
}