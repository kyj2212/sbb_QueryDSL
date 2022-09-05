package site.yejin.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.interestkeyword.InterestKeyword;
import site.yejin.sbb.interestkeyword.InterestKeywordId;
import site.yejin.sbb.interestkeyword.InterestKeywordRepository;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.entity.MemberRole;
import site.yejin.sbb.member.repository.MemberRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
// 이렇게 클래스 @Transactional를 붙이면, 클래스의 각 테스트케이스에 전부 @Transactional 붙은 것과 동일
// @Test + @Transactional 조합은 자동으로 롤백을 유발시킨다.
@Transactional
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InterestKeywordRepository interestKeywordRepository;

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

    @Test
    @DisplayName("특정회원의 follower들과 following들을 모두 알 수 있어야 한다.")
    @Rollback(false)
    void t15() {
        Member u1 = memberRepository.getQslMember(1L);
        Member u2 = memberRepository.getQslMember(2L);

        u1.follow(u2);

        // follower
        // u1의 구독자 : 0
        assertThat(u1.getFollowers().size()).isEqualTo(0);

        // follower
        // u2의 구독자 : 1
        assertThat(u2.getFollowers().size()).isEqualTo(1);

        // following
        // u1이 구독중인 회원 : 1
        assertThat(u1.getFollowings().size()).isEqualTo(1);

        // following
        // u2가 구독중인 회원 : 0
        assertThat(u2.getFollowings().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("지울때 고아까지 지워지는지, no JPA")
    @Rollback(false)
    void test_delete(){
        Member u1 = memberRepository.getQslMember(1L);
        System.out.printf(u1.getName()+" : ");
        u1.getInterestKeywords().forEach(k -> System.out.printf(k.getContent()+" "));
        System.out.println();

        System.out.println("member 의 interestkeywords 멤버가 interestkeyword를 여러개 가지고 있는 것이므로 onetomany");
        System.out.println("이 멤버가 지워지면 이 멤버가 가지고 있는 interestkeywords는 다 지워져야 한다.");

        System.out.println("반대로, interestkeyword 가 지워지면, member 에서 member가 가지고 있는 interestkeyword가 지워져야 한다.");
        InterestKeyword interestKeyword = new InterestKeyword(u1,"농구");
        u1.removeInterestKeyword(interestKeyword);
        System.out.printf(u1.getName()+" : ");
        u1.getInterestKeywords().forEach(k -> System.out.printf(k.getContent()+" "));
        System.out.println();
    }

    @Test
    @DisplayName("지울때 고아까지 지워지는지, JPA")
    @Rollback(false)
    void test_delete_jpa(){
        Member u1 = memberRepository.getQslMember(1L);
        System.out.printf(u1.getName()+" : ");
        u1.getInterestKeywords().forEach(k -> System.out.printf(k.getContent()+" "));
        System.out.println();

        System.out.println("member 의 interestkeywords 멤버가 interestkeyword를 여러개 가지고 있는 것이므로 onetomany");
        System.out.println("이 멤버가 지워지면 이 멤버가 가지고 있는 interestkeywords는 다 지워져야 한다.");

        System.out.println("반대로, interestkeyword 가 지워지면, member 에서 member가 가지고 있는 interestkeyword가 지워져야 한다.");
        InterestKeyword interestKeyword = interestKeywordRepository.findById(new InterestKeywordId(u1,"농구")).orElseThrow();
        interestKeywordRepository.delete(interestKeyword);
        System.out.printf(u1.getName()+" : ");
        u1.getInterestKeywords().forEach(k -> System.out.printf(k.getContent()+" "));
        System.out.println();
    }


    @Test
    @DisplayName("u8이 팔로우한 사람들의 관심종목 중복없이 조회하기")
    @Rollback(false)
    void test_select_followings_interest(){
        Member m1 = memberRepository.getQslMember(8L);
        System.out.println("u8 이 팔로우한 사람들");
        m1.getFollowings().forEach(f -> System.out.println(f.getName()));
        m1.getFollowings().forEach(f -> System.out.println(f.getName()+"의 interest : "+f.getInterestKeywords()+" list : "+Arrays.asList(f.getInterestKeywords())));



    }

}
