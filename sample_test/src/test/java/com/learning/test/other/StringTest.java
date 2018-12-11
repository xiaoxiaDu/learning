package com.learning.test.other;

import com.learning.test.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 字符串测试类
 * Created by Administrator on 2018/12/10.
 */
public class StringTest {

    @Test
    public void testStringFormat() {
        /**
         转换符 说明 示例
         %s 字符串类型 "mingrisoft"
         %c 字符类型 'm'
         %b 布尔类型 true
         %d 整数类型（十进制） 99
         %x 整数类型（十六进制） FF
         %o 整数类型（八进制） 77
         %f 浮点类型 99.99
         %a 十六进制浮点类型 FF.35AE
         %e 指数类型 9.38e+5
         %g 通用浮点类型（f和e类型中较短的）
         %h 散列码
         %% 百分比类型 ％
         %n 换行符
         %tx 日期与时间类型（x代表不同的日期与时间转换符
         */

        String aa = null;
        String bb = "";
        String contractMessage = String.format("contractType:%s, contractNo:%s", aa, bb);
        System.out.println(contractMessage);

        String contractNo = "LYPH-20181011T-00001-001";
        String contractType = "15";

        String mobile = "15210082788,15210082788";
        String contentTemplate = "您好,合同编号:%s,合同类型:%s的合同生成失败请及时处理.时间:%s";
        String content = String.format(contentTemplate, contractNo, contractType, DateUtil.getCurrentDayTime24Formatt());

        System.out.println("content: " + content);

        String difference = StringUtils.difference("abc", "abcd");
        System.out.println(difference);
    }
}
