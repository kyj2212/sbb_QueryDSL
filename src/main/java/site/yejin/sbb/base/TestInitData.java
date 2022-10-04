//package site.yejin.sbb.base;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import site.yejin.sbb.member.entity.Member;
//import site.yejin.sbb.member.repository.MemberRepository;
//
//import java.util.Arrays;
//
//@Slf4j
//@Configuration
//@Profile("test") // 이 클래스 정의된 Bean 들은 test 모드에서만 활성화 된다.
//public class TestInitData {
//    // CommandLineRunner : 주로 앱 실행 직후 초기데이터 세팅 및 초기화에 사용
//
//    @Bean
//    ApplicationRunner init(MemberRepository memberRepository){
//        log.debug("테스트 데이터 초기화 by ApplicationRunner");
//        return args -> {
//            Member u1 = Member.builder()
//                    .username("user1")
//                    .password("{noop}1234")
//                    .name("사용자1")
//                    .email("user1@test.com")
//                    .build();
//
//            Member u2 = Member.builder()
//                    .username("user2")
//                    .password("{noop}1234")
//                    .name("사용자2")
//                    .email("user2@test.com")
//                    .build();
//
//            Member u3 = Member.builder()
//                    .username("user3")
//                    .password("{noop}1234")
//                    .name("사용자3")
//                    .email("user3@test.com")
//                    .build();
//
//            Member u4 = Member.builder()
//                    .username("user4")
//                    .password("{noop}1234")
//                    .name("사용자4")
//                    .email("user4@test.com")
//                    .build();
//
//            Member u5 = Member.builder()
//                    .username("user5")
//                    .password("{noop}1234")
//                    .name("사용자5")
//                    .email("user5@test.com")
//                    .build();
//
//            Member u6 = Member.builder()
//                    .username("user6")
//                    .password("{noop}1234")
//                    .name("사용자6")
//                    .email("user6@test.com")
//                    .build();
//
//            Member u7 = Member.builder()
//                    .username("user7")
//                    .password("{noop}1234")
//                    .name("사용자7")
//                    .email("user7@test.com")
//                    .build();
//
//            Member u8 = Member.builder()
//                    .username("user8")
//                    .password("{noop}1234")
//                    .name("사용자8")
//                    .email("user8@test.com")
//                    .build();
//            Member u9 = Member.builder()
//                    .username("user9")
//                    .password("{noop}1234")
//                    .name("사용자9")
//                    .email("user9@test.com")
//                    .build();
//
//            u1.addInterestKeywordContent("축구");
//            u1.addInterestKeywordContent("농구");
//
//            u2.addInterestKeywordContent("클라이밍");
//            u2.addInterestKeywordContent("마라톤");
//            u2.addInterestKeywordContent("농구");
//
//            u3.addInterestKeywordContent("피아노");
//            u4.addInterestKeywordContent("기타");
//            u5.addInterestKeywordContent("기타");
//            u6.addInterestKeywordContent("베이스");
//
//            memberRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8,u9)); // PERSIST
//
//
//            u8.follow(u7);
//            u8.follow(u6);
//            u8.follow(u5);
//            u8.follow(u4);
//            u8.follow(u3);
//            u8.follow(u1);
//
//            u7.follow(u6);
//            u7.follow(u5);
//            u7.follow(u4);
//            u7.follow(u3);
//
//            memberRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8,u9)); // MERGE
//        };
//    }
////    @Bean
////    CommandLineRunner init(MemberRepository memberRepository) {
////        log.debug("테스트 데이터 insert 시작");
////        return args -> {
////            Member u1 = Member.builder()
////                    .username("user1")
////                    .password("{noop}1234")
////                    .name("사용자1")
////                    .email("user1@test.com")
////                    .build();
////
////            Member u2 = Member.builder()
////                    .username("user2")
////                    .password("{noop}1234")
////                    .name("사용자2")
////                    .email("user2@test.com")
////                    .build();
////
////            Member u3 = Member.builder()
////                    .username("user3")
////                    .password("{noop}1234")
////                    .name("사용자3")
////                    .email("user3@test.com")
////                    .build();
////
////            Member u4 = Member.builder()
////                    .username("user4")
////                    .password("{noop}1234")
////                    .name("사용자4")
////                    .email("user4@test.com")
////                    .build();
////
////            Member u5 = Member.builder()
////                    .username("user5")
////                    .password("{noop}1234")
////                    .name("사용자5")
////                    .email("user5@test.com")
////                    .build();
////
////            Member u6 = Member.builder()
////                    .username("user6")
////                    .password("{noop}1234")
////                    .name("사용자6")
////                    .email("user6@test.com")
////                    .build();
////
////            Member u7 = Member.builder()
////                    .username("user7")
////                    .password("{noop}1234")
////                    .name("사용자7")
////                    .email("user7@test.com")
////                    .build();
////
////            Member u8 = Member.builder()
////                    .username("user8")
////                    .password("{noop}1234")
////                    .name("사용자8")
////                    .email("user8@test.com")
////                    .build();
////
////            u1.addInterestKeywordContent("축구");
////            u1.addInterestKeywordContent("농구");
////
////            u2.addInterestKeywordContent("클라이밍");
////            u2.addInterestKeywordContent("마라톤");
////            u2.addInterestKeywordContent("농구");
////
////            u3.addInterestKeywordContent("피아노");
////            u4.addInterestKeywordContent("기타");
////            u5.addInterestKeywordContent("기타");
////            u6.addInterestKeywordContent("베이스");
////
////            memberRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8)); // PERSIST
////
////
////            u8.follow(u7);
////            u8.follow(u6);
////            u8.follow(u5);
////            u8.follow(u4);
////            u8.follow(u3);
////            u8.follow(u1);
////
////            u7.follow(u6);
////            u7.follow(u5);
////            u7.follow(u4);
////            u7.follow(u3);
////
//////            Set<Member> members = new HashSet<>();
//////            members.add(u7);
//////            members.add(u6);
//////            members.add(u5);
//////            members.add(u4);
//////            members.add(u3);
//////            members.add(u1);
//////
//////            Set<Member> members2 = new HashSet<>();
//////            members2.add(u6);
//////            members2.add(u5);
//////            members2.add(u4);
//////            members2.add(u3);
//////
//////            Following f1 = Following.builder().id(u8.getId()).members(members).build();
//////            Following f2 = Following.builder().id(u7.getId()).members(members2).build();
//////            followingRepository.saveAll(Arrays.asList(f1,f2));
////
////            memberRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8)); // MERGE
////        };
////    }
//}
