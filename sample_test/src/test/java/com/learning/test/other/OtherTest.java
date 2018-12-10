package com.learning.test.other;

import org.junit.Test;

/**
 * 其他测试类
 * Created by Administrator on 2018/12/10.
 */
public class OtherTest {

    @Test
    public void testSubway(){
        Integer month = 25;// 25天
        Integer everyDayTimes = 2;// 每天的次数
        Integer monthTimes = month * everyDayTimes;// 一个月的次数

        Double subWayMoney1 = 5.0d;// 单程票价
        Double subWayMoney2 = 4.0d;// 单程票价 8折
        Double subWayMoney3 = 2.5d;// 单程票价 5折

        Double baseMoney1 = 150d;// 基数
        Double baseMoney2 = 200d;// 基数

        Double totalMoney = 0.0d;
        for (int i = 1; i <= monthTimes; i++){
            Double money = 5.0d;
            if(baseMoney1 <= totalMoney && totalMoney < baseMoney2){
                // 150 -- 200
                money = subWayMoney2;
            } else if (baseMoney2 <= totalMoney){
                // 200 以上
                money = subWayMoney3;
            } else {

                money = subWayMoney1;
            }
            totalMoney = totalMoney + money;
            System.out.println("次数: " + i + " 单程票价: " + money + " 总票价: " + totalMoney);
        }
        System.out.println("地铁总票价: " + totalMoney);// 220

        Double busMoney = 1.0d;
        Double dayBusMoney = busMoney * monthTimes;
        System.out.println("公交车总票价: " + dayBusMoney);// 50

        Double total = dayBusMoney + totalMoney;
        System.out.println("每个月的通勤费用: " + total);// 270

    }
}
