package com.learning.test.utils;

import org.junit.Test;

/**
 *
 * Created by Administrator on 2018/12/11.
 */
public class KKAESTest {

    @Test
    public void testKKAES(){

        String value = "431102198605223019";
        String encrypt = KKAES.encrypt(value);
        System.out.println("value=" + value + ", encrypt=" + encrypt);

        String val = "ehNlir/OwtHd/p/i5HLEhMj8bXJASMvYKb0A88KpEbg=";
        String decrypt = KKAES.decrypt(val);
        System.out.println("val=" + val + ", decrypt=" + decrypt);

    }
}
