package site.yejin.sbb.base;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;

@Configuration
public class TestInitDataConfig {
    @Autowired
    private MemberRepository memberRepository;
    @Bean
    public InitializingBean init(){
        return () -> {
            Member u1 = Member.builder()
                    .username("user1")
                    .password("{noop}1234")
                    .name("사용자1")
                    .email("user1@test.com")
                    .build();

            memberRepository.save(u1);
        };
    }
}
