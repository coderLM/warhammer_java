package design;

import java.util.HashMap;
import java.util.Map;

/**
 * 解释器模式
 * 目的：自定义文法被配套解释器执行，表达某种行为
 * 组成：
 * 抽象表达式：所有的具体表达式角色（运算符、常量、变量）都需要实现的抽象接口。
 * 非终结符表示：运算符
 * 终结符表示：变量、常量
 * Context：存储变量的值
 */
public class ExplainDemo extends BaseDemo {
    @Override
    public void run() {
        Context context = new Context();
        Constant constantA =new Constant(true);
        Constant constantB =new Constant(false);
        Variable variableA = new Variable();
        Variable variableB = new Variable();
        context.define(variableA,true);
        context.define(variableB,false);

        System.out.println(constantA.value);
        System.out.println(new Or(constantB,variableA).interpret(context));
        System.out.println(new And(constantA,variableB).interpret(context));
        System.out.println(new Not(constantA).interpret(context));
    }

    //解释操作 抽象
    abstract class Expression {
        public abstract boolean interpret(Context context);//解释操作
    }

    //上下文
    class Context {
        Map<Variable, Boolean> map = new HashMap<>();

        public boolean lookup(Variable variable) {
            return map.get(variable);
        }

        public void define(Variable variable, boolean value) {
            map.put(variable, value);
        }
    }

    //布尔常量
    class Constant extends Expression {
        boolean value;

        public Constant(boolean value) {
            this.value = value;
        }

        @Override
        public boolean interpret(Context context) {
            return value;
        }
    }

    //运算符
    class And extends Expression {
        Expression left, right;
        public And(Expression left,Expression right){
            this.left=left;
            this.right=right;
        }
        @Override
        public boolean interpret(Context context) {
            return left.interpret(context) && right.interpret(context);
        }
    }

    class Or extends Expression {
        Expression left, right;
        public Or(Expression left,Expression right){
            this.left=left;
            this.right=right;
        }
        @Override
        public boolean interpret(Context context) {
            return left.interpret(context) || right.interpret(context);
        }
    }

    class Not extends Expression {
        Expression value;
        public Not(Expression value){
            this.value=value;
        }
        @Override
        public boolean interpret(Context context) {
            return !value.interpret(context);
        }
    }

    //变量
    class Variable extends Expression {

        @Override
        public boolean interpret(Context context) {
            return context.lookup(this);
        }
    }

}
