package site.yejin.sbb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.answer.Answer;
import site.yejin.sbb.answer.AnswerRepository;
import site.yejin.sbb.global.exception.SignupEmailDuplicatedException;
import site.yejin.sbb.global.exception.SignupUsernameDuplicatedException;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionRepository;

import java.time.LocalDateTime;

public class ClearDataUtil {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private static int lastTestQuestionId;
    private static int lastTestAnswerQuestionId;
    private static int lastTestAnswerId;
    private static long lastTestMemberId;
    private static Member lastTestMember;

    private void clearData() {
        clearData(memberRepository, answerRepository, questionRepository);
    }

    public static void clearData(MemberRepository memberRepository, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        clearAnswer(answerRepository);
        clearQuestion(questionRepository);
        clearMember(memberRepository);
    }

    private static void clearMember(MemberRepository memberRepository){
        memberRepository.deleteAll(); // DELETE FROM site_user;
        memberRepository.truncateTable();
    }

    public static void clearAnswer(AnswerRepository answerRepository) {
        answerRepository.deleteAll(); // DELETE FROM question;
        answerRepository.truncate();
    }
    public static void clearQuestion(QuestionRepository questionRepository) {
        questionRepository.deleteAll(); // DELETE FROM question;
        questionRepository.truncate();
    }

    @Transactional
    private void createSampleData() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        createSampleData(memberService,answerRepository,questionRepository);
    }

    @Transactional
    public static void createSampleData(MemberService memberService, AnswerRepository answerRepository, QuestionRepository questionRepository)  throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        createMemberSampleData(memberService);
        createQuestionSampleData(questionRepository,answerRepository);
        createAnswerSampleData(questionRepository,answerRepository);
    }

    @Transactional
    public static void createMemberSampleData(MemberService memberService) throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        memberService.create("admin", "1234","관리자","admin@test.com");
        memberService.create("user1", "1234","유저1","user1@test.com");
        lastTestMember=memberService.findByUsername("user1");
    }
    @Transactional
    public static void createQuestionSampleData(QuestionRepository questionRepository, AnswerRepository answerRepository) {

        Question q1 = new Question();
        q1.initAnswerList();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(lastTestMember);
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.initAnswerList();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        q2.setAuthor(lastTestMember);
        questionRepository.save(q2);

        lastTestQuestionId=q2.getId();

    }

    @Transactional
    private static void createAnswerSampleData(QuestionRepository questionRepository, AnswerRepository answerRepository) {

        Question q = questionRepository.findById(1).get();
        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setQuestion(q);
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트관련 내용을 다룹니다.");
        a2.setQuestion(q);
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);
        answerRepository.save(a2);

        lastTestAnswerId=a2.getId();
        lastTestAnswerQuestionId=a2.getQuestion().getId();

    }



}
