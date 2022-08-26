package site.yejin.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.answer.AnswerRepository;
import site.yejin.sbb.global.exception.SignupEmailDuplicatedException;
import site.yejin.sbb.global.exception.SignupUsernameDuplicatedException;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.question.QuestionRepository;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        clearData();
        createSampleData();

    }

    private void clearData() {
        ClearDataUtil.clearData(memberRepository,answerRepository,questionRepository);
    }

    private void createSampleData() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        createSampleData(memberService);
    }

    public static void createSampleData(MemberService memberService) throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        memberService.create("admin", "1234","관리자","admin@test.com");
        memberService.create("user1", "1234","유저1","user1@test.com");
    }


    @Test
    @DisplayName("회원가입이 가능하다.")
    public void t1() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        memberService.create("user2", "1234","유저2","user2@test.com");

    }

    @Test
    public void test_findByUsername(){
        this.memberService.findByUsername("user1");
    }
}