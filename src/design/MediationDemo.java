package design;

/**
 * 中介模式
 * 场景：多对象间复杂交互
 * 目的：解耦
 * 缺点：
 * 1.中介者复杂度高
 * 2.风险高，中介者出问题，整个交互出问题
 */
public class MediationDemo extends BaseDemo{
    @Override
    public void run() {
        WorkerMediator mediator= new WorkerMediator();
        Worker workerA = new WorkerA(mediator);
        Worker workerB = new WorkerB(mediator);
        mediator.setWorkerA(workerA);
        mediator.setWorkerB(workerB);
        workerA.sendMsg();

    }
    abstract class Mediator{
        public abstract void send(String msg,Worker worker);
    }
    abstract class Worker{
        Mediator mediator;
        public Worker(Mediator mediator){
            this.mediator=mediator;
        }
        public abstract void sendMsg();
        public abstract void getMsg(String msg);
    }
    class WorkerMediator extends Mediator{
        Worker workerA;
        Worker workerB;
        @Override
        public void send(String msg, Worker worker) {
            if(worker instanceof WorkerA){
                workerB.getMsg(msg);
            }else if(worker instanceof  WorkerB){
                workerA.getMsg(msg);
            }
        }
        public void setWorkerA(Worker workerA){
            this.workerA=workerA;
        }

        public void setWorkerB(Worker workerB) {
            this.workerB = workerB;
        }
    }
    class WorkerA extends Worker{

        public WorkerA(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void sendMsg() {
            String msg ="gogogo!";
            System.out.println("workerA sendMsg:"+msg);
            mediator.send(msg,this);
        }


        @Override
        public void getMsg(String msg) {
            System.out.println("workerA getMsg:"+msg);
        }
    }
    class WorkerB extends Worker{

        public WorkerB(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void sendMsg() {
            String msg ="ok, I'm coming!";
            System.out.println("workerB sendMsg:"+msg);
            mediator.send(msg,this);
        }

        @Override
        public void getMsg(String msg) {
            System.out.println("workerB getMsg:"+msg);
            if(msg.equals("gogogo!")){
                sendMsg();
            }
        }
    }

}

