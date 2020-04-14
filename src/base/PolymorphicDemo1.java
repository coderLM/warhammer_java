package base;

import java.util.ArrayList;
import java.util.List;

/**
 * 多态
 */
public class PolymorphicDemo1 {
    public static void main(String[] args){
        Person student = new Student();
        Person teacher =new Teacher();
        List<Person> list = new ArrayList<>();
        list.add(student);
        list.add(teacher);
        for(Person p : list){
            p.action();
        }
    }

}
class Person{
    public void action(){
        System.out.println("live");
    }
}
class Student extends Person{
    @Override
    public void action() {
        System.out.println("study");
    }
}
class Teacher extends  Person{
    @Override
    public void action() {
        System.out.println("teach");
    }
}
