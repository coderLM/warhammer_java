package suntime;

import base.Singleton;

import java.util.Calendar;
import java.util.Random;

public class SunTimeTest {
    int year = 2020;
    int month = 4;
    int day = 20;
//    double lat = 39.9054;
//    double lon = 116.4098;
    double lat = 50.6860D;
    double lon = 156.3187D;

    public static void main(String[] args) {
        SunTimeTest sunTimeTest = new SunTimeTest();
//        sunTimeTest.testSunriseSunset();
//        sunTimeTest.getSolarEquationVariables();
        sunTimeTest.testOne();
//        sunTimeTest.testAll();
    }

    void testSunriseSunset() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Calendar[] sunriseSunset = SunriseSunset.getSunriseSunset(calendar, lat, lon);
        System.out.println("rise:" + sunriseSunset[0].getTimeInMillis());
        System.out.println("set:" + sunriseSunset[1].getTimeInMillis());
    }
    void getSolarEquationVariables(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SunriseSunset.SolarEquationVariables solarEquationVariables = SunriseSunset.getSolarEquationVariables(calendar, lon);
        System.out.println(solarEquationVariables.toString());
    }
    void testOne(){
        ///todo  getInstance 返回的是 当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Calendar[] sunriseSunset = SunriseSunset.getSunriseSunset(calendar, lat, lon);
        System.out.println("rise:" + sunriseSunset[0].getTimeInMillis());
        System.out.println("set:" + sunriseSunset[1].getTimeInMillis());
    }
    void testAll(){
    }
}
