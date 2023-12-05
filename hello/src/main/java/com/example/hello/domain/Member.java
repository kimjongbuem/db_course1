package com.example.hello.domain;

import lombok.Builder;
import lombok.Data;

/*
 * Database Member Entity 
 * 
 */
@Data
@Builder
public class Member {

    // pk : member id //
    private String memberId;

    // data field //
    private long money;
}
