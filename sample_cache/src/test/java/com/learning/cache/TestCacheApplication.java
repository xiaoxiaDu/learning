package com.learning.cache;

import com.learning.cache.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * test
 * Created by Administrator on 2018/12/21.
 */
@SpringBootTest(classes = CacheApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCacheApplication {

    @Autowired
    private MemberService memberService;


    @Test
    public void test(){

        String member = memberService.getMember(126L);
        System.out.println(member);
    }

    @Test
    public void testu(){

        int i = memberService.updateMember(126L);
        System.out.println("======, " + i);
    }
}
