package site.yejin.sbb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner init(MemberRepository memberRepository){
        return args -> {
            Member m1 = Member.builder()
                    .name("테스트1")
                    .username("test1")
                    .password("(noop)1234")
                    .email("test1@mail.com")
                    .build();
            Member m2 = Member.builder()
                    .name("테스트2")
                    .username("test2")
                    .password("(noop)1234")
                    .email("test2@mail.com")
                    .build();
            //memberRepository.saveAll(Arrays.asList(m1,m2));
            List<Member> members = memberRepository.saveAll(Arrays.asList(m1,m2));
        };
    }
}
