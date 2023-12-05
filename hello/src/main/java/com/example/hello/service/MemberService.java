package com.example.hello.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.hello.domain.Member;
import com.example.hello.repository.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final Repository<Member> memberRepository;

    public List<Member> find() {
        return memberRepository.find();
    }

    @Transactional
    public void save(Member member) {

        try {
            memberRepository.save(member);
        } catch (DuplicateKeyException e) {
            log.error("[ERROR] Duplicated Key : {}", member.getMemberId());
            String newId = member.getMemberId() + new Random(System.currentTimeMillis()).nextInt(100);
            log.info("New id creating >>> newId[{}]", newId);
            memberRepository.save(Member.builder().memberId(newId).money(0).build());
        }

    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        accountTransfers(fromId, toId, money);
    }

    private void accountTransfers(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);

        Member toMember = memberRepository.findById(toId);

        if (fromMember.getMoney() - money < 0) {
            throw new IllegalArgumentException("Sending money is lower.");
        }

        // from-member의 전송할 돈의 액수를 감소 //
        memberRepository.update(Member.builder().memberId(fromId).money(fromMember.getMoney() - money).build());

        // to-member의 전송할 돈의 액수를 증액 //
        memberRepository.update(Member.builder().memberId(toId).money(toMember.getMoney() + money).build());
    }

}
