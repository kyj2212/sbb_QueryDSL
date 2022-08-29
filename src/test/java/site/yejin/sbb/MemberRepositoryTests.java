package site.yejin.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.entity.MemberRole;
import site.yejin.sbb.member.repository.MemberRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    private static final long sampleMemberId=1;

    @Test
    void test_delete_member(){
        memberRepository.delete(memberRepository.findByUsername("test1").orElseThrow(() -> new RuntimeException("%s 해당 유저가 없습니다.".formatted("테스트1"))));
        memberRepository.delete(memberRepository.findByUsername("test2").orElseThrow(() -> new RuntimeException("%s 해당 유저가 없습니다.".formatted("테스트2"))));

    }


    @Test
    @DisplayName("회원 생성")
    void test_create_member(){
        Member m1 = new Member("test3","(noop)1234", "테스트3", "test3@mail.com");
        Member m2 = new Member("test4","(noop)1234", "테스트4", "test4@mail.com");
        // (noop) 은 encryption 하지 않은 default 값을 의마하는 sql 용어인가?
        memberRepository.saveAll(Arrays.asList(m1,m2));
        System.out.println("sampleMemberId : "+sampleMemberId);
        System.out.println("sampleMember username : "+m1.getUsername());
        // save(m1); save(m2) --> saveAll(Arrays.asList(m1,m2));
    }
    @Test
    void test_select_byId_noQsl(){
        System.out.println("sampleMemberId : "+sampleMemberId);
        Member member=memberRepository.findById(sampleMemberId).orElseThrow(()->new RuntimeException("no member"));
        System.out.println("sampleMember username : "+member.getUsername());
        assertThat(member.getUsername()).isEqualTo("test1");
    }

    @Test
    void test_select_byId_Qsl(){
        System.out.println("sampleMemberId : "+sampleMemberId);
        Member member=memberRepository.getQslMember(sampleMemberId);
        System.out.println("sampleMember username : "+member.getUsername());
        assertThat(member.getUsername()).isEqualTo("test1");
    }

}
