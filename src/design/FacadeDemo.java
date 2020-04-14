package design;

/**
 * 外观模式
 * 目的：降低耦合性，简化子系统调用（定义高层接口，使子系统易用）
 * 使用场景：
 * 1.系统设计初期
 * 2.开发过程中子系统变得越来越复杂
 * 3.使用一个比较复杂的遗留大系统时，用facade提供简明的接口
 */
public class FacadeDemo extends BaseDemo {
    @Override
    public void run() {
        ComputerServer server = new ComputerServer();
        server.buy();
    }

    class ComputerServer {
        public void buy() {
            CpuCompany cpuCompany = new CpuCompany();
            cpuCompany.buyCpu();
            BoardCompany boardCompany = new BoardCompany();
            boardCompany.buyBoard();
        }
    }

    class CpuCompany {
        public void buyCpu() {
            System.out.println("buy cpu");
        }
    }

    class BoardCompany {
        public void buyBoard() {
            System.out.println("buy main board");
        }
    }

}
