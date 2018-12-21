package com.learning.cache.service;

/**
 *
 * Created by Administrator on 2018/12/21.
 */
public interface MemberService {

    String getMember(Long userId);

    int updateMember(Long userId);
}
