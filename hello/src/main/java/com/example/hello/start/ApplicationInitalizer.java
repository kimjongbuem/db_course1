package com.example.hello.start;

import javax.annotation.PostConstruct;

/*
 * Application Initalizer
 */

// @RequiredArgsConstructor
public class ApplicationInitalizer{
     
    // private final H2DatabaseInfo h2DatabaseInfo;

    // private DBUtil dbUtil;

    // private final MemberRepository memberRepository;
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        System.out.println("----start application initalizer----");
    }
}
