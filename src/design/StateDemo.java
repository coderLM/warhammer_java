package design;

/**
 * 状态模式
 * 目的：当状态影响对象行为时，将行为抽象到状态类，消除庞大的分支语句
 * PS：也可以把状态之间的切换放到State子类中
 */
public class StateDemo extends BaseDemo {
    @Override
    public void run() {
        Person person = new Person();
        person.behavior();
        person.inNoon();
        person.behavior();
        person.inNight();
        person.behavior();
    }

    interface State {
        void action();
    }
    class MorningState implements State{
        @Override
        public void action() {
            System.out.println("on the way");
        }
    }

    class NoonState implements State {
        @Override
        public void action() {
            System.out.println("work at company");
        }
    }

    class NightState implements State {

        @Override
        public void action() {
            System.out.println("study at home");
        }
    }

    class Person {
        public Person() {
            inMorning();
        }

        State currentState;

        void setCurrentState(State currentState) {
            this.currentState = currentState;
        }

        void behavior() {
            this.currentState.action();
        }
        void inMorning(){
            setCurrentState(new MorningState());
        }
        void inNoon(){
            setCurrentState(new NoonState());
        }
        void inNight(){
            setCurrentState(new NightState());
        }
    }

}
