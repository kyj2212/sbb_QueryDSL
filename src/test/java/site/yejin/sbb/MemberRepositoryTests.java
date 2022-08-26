package site.yejin.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;

import java.util.Arrays;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 생성")
    void test_create_member(){
        Member m1 = new Member("test1", "(noop)1234", "테스트1", "test1@mail.com");
        Member m2 = new Member("test2", "(noop)1234", "테스트2", "test2@mail.com");
        // (noop) 은 encryption 하지 않은 default 값을 의마하는 sql 용어인가?
        memberRepository.saveAll(Arrays.asList(m1,m2));
        // save(m1); save(m2) --> saveAll(Arrays.asList(m1,m2));
    }

    @Test
    void test_delete_member(){
        memberRepository.delete(memberRepository.findByUsername("test1").orElseThrow(() -> new RuntimeException("%s 해당 유저가 없습니다.".formatted("테스트1"))));
        memberRepository.delete(memberRepository.findByUsername("test2").orElseThrow(() -> new RuntimeException("%s 해당 유저가 없습니다.".formatted("테스트2"))));

    }

}
