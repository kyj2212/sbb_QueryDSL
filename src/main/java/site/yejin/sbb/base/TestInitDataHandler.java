//package site.yejin.sbb.base;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Component;
//import site.yejin.sbb.member.entity.Member;
//import site.yejin.sbb.member.repository.MemberRepository;
//
//import java.util.Arrays;
//
//@Component
//@Slf4j
//public class TestInitDataHandler {
//	@Autowired
//	private MemberRepository memberRepository;
//
//	@EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        log.debug("[Event Listener] ApplicationReadyEvent listen");
//		Member u1 = Member.builder()
//				.username("user1")
//				.password("{noop}1234")
//				.name("사용자1")
//				.email("user1@test.com")
//				.build();
//
//		Member u2 = Member.builder()
//				.username("user2")
//				.password("{noop}1234")
//				.name("사용자2")
//				.email("user2@test.com")
//				.build();
//		memberRepository.saveAll(Arrays.asList(u1,u2));
//    }
//}
