//package site.yejin.sbb.base;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import site.yejin.sbb.member.entity.Member;
//import site.yejin.sbb.member.repository.MemberRepository;
//
//@Slf4j
//@Component
//public class TestInitDataDao implements InitializingBean {
//    @Autowired
//    private MemberRepository memberRepository;
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        log.debug("[Event Listener] ApplicationReadyEvent listen");
//		Member u1 = Member.builder()
//				.username("user1")
//				.password("{noop}1234")
//				.name("사용자1")
//				.email("user1@test.com")
//				.build();
//
//		memberRepository.save(u1);
//    }
//}
