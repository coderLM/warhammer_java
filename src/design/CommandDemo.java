package design;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式
 * 目的：动态任务管理
 * 1.分离命令的发起与执行
 * 2.管理命令
 * 3.生成日志
 *
 */
public class CommandDemo extends BaseDemo{
    @Override
    public void run() {
        Executor executor=new RealExecutor();
        Manager manager = new Manager();
        manager.addCommand(new DownloadCommand(executor));
        manager.addCommand(new DownloadCommand(executor));
        manager.doCommand();
    }
    abstract class Command{
        Executor executor;
        public Command(Executor executor){
            this.executor=executor;
        }
        public abstract void execute();
    }
    abstract class Executor{
        public abstract void execute(String task);
    }
    class DownloadCommand extends Command{

        public DownloadCommand(Executor executor) {
            super(executor);
        }

        @Override
        public void execute() {
            executor.execute("download task");
        }
    }
    class RealExecutor extends Executor{

        @Override
        public void execute(String task) {
            System.out.println(task);
        }
    }
    class Manager{
        public List<Command> list  =new ArrayList<>();
        public void addCommand(Command command){
            list.add(command);
        }
        public void cancelCommand(Command command){
            list.remove(command);
        }
        public void doCommand(){
            for(Command command:list){
                command.execute();
            }
        }
    }
}
