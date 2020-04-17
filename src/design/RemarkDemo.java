package design;

/**
 * 备忘录模式
 * 目的：保存对象状态以复用
 */
public class RemarkDemo extends BaseDemo {

    @Override
    public void run() {
        Game game=new Game();
        game.run();
        game.run();
        MarkManager markManager = new MarkManager();
        markManager.saveMark(game.createMark());
        game.died();
        game.reuseMark(markManager.findMark());
        game.run();
    }

    class Game {
        int blood;
        int attackPower;

        public Mark createMark() {
            return new Mark(blood, attackPower);
        }

        public void reuseMark(Mark mark) {
            this.blood = mark.getBlood();
            this.attackPower = mark.getAttackPower();
        }

        public void run() {
            System.out.println("running blood:"+blood+" attackPower:"+attackPower);
            blood += 1;
            attackPower += 1;
        }
        public void died(){
            blood=0;
            attackPower=0;
            System.out.println("died blood:"+blood+" attackPower:"+attackPower);
        }
    }

    class MarkManager {
        Mark mark;

        public void saveMark(Mark mark) {
            this.mark = mark;
        }

        public Mark findMark() {
            return this.mark;
        }
    }

    class Mark {
        public Mark(int blood, int attackPower) {
            this.blood = blood;
            this.attackPower = attackPower;
        }

        int blood;
        int attackPower;

        public int getBlood() {
            return blood;
        }

        public int getAttackPower() {
            return attackPower;
        }
    }

}
