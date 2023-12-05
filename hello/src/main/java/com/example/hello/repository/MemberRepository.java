package com.example.hello.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.hello.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberRepository implements Repository<Member> {
    private final JdbcTemplate jdbcTemplate;

    /*
     * 목록 조회
     */
    @Override
    public List<Member> find() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return Member
                    .builder()
                    .memberId(rs.getString("member_id"))
                    .money(rs.getInt("money"))
                    .build();
        });
    }

    /*
     * 단일 조회
     * 
     * @param memberId
     */
    @Override
    public Member findById(String memberId) {
        String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return Member
                    .builder()
                    .memberId(rs.getString("member_id"))
                    .money(rs.getInt("money"))
                    .build();
        }, memberId);
    }

    /*
     * 추가
     * 
     * @param Member
     */
    @Override
    public void save(Member member) {
        String sql = "insert into MEMBER(MEMBER_ID, MONEY) values(?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
    }

    /*
     * 수정
     * 
     * @param Member
     */
    public void update(Member member) {
        String sql = "UPDATE MEMBER SET MONEY= ? WHERE MEMBER_ID = ?";
        jdbcTemplate.update(sql, member.getMoney(), member.getMemberId());
    }

    /*
     * 삭제
     * 
     * @param Member
     */
    public void delete(String memberId) {
        String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";
        jdbcTemplate.update(sql, memberId);
    }
}
