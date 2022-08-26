package site.yejin.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.answer.AnswerRepository;
import site.yejin.sbb.global.exception.SignupEmailDuplicatedException;
import site.yejin.sbb.global.exception.SignupUsernameDuplicatedException;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.repository.MemberRepository;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class QuestionRepoTests {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
//    @Autowired
//    private static Principal principal;

   //System.out.println("사용자"+ principal.getName());

    private static Member member=new Member();
    private static int lastSampleDataId;

    @BeforeEach
    void beforeEach() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        clearData();
        createSampleData();
    }

    private void clearData() {
        ClearDataUtil.clearData(memberRepository,answerRepository,questionRepository);
    }
    private void createSampleData() throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        lastSampleDataId = createSampleData(memberService,questionRepository);
    }
    public static int createSampleData(MemberService memberService, QuestionRepository questionRepository) throws SignupUsernameDuplicatedException, SignupEmailDuplicatedException {
        MemberServiceTests.createSampleData(memberService);

        member=memberService.findByUsername("user1");
        Question q1 = new Question();
        q1.initAnswerList();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(member);
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.initAnswerList();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        q2.setAuthor(member);
        questionRepository.save(q2);

        return q2.getId();
    }

    public static void clearData(QuestionRepository questionRepository) {
        questionRepository.deleteAll(); // DELETE FROM question;
        questionRepository.truncate();
    }



    @Test
    void test_clearData(){
        clearData();
    }

    @Test
    void test_save() {


        Question q1 = new Question();
        q1.initAnswerList();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        q1.setAuthor(member);
      //  q1.setAuthor(memberService.findByUsername(principal.getName()));

        questionRepository.save(q1);

        Question q2 = new Question();
        q2.initAnswerList();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        q2.setAuthor(member);
        //     q2.setAuthor(memberService.findByUsername(principal.getName()));
        questionRepository.save(q2);

        assertThat(q1.getId()).isEqualTo(lastSampleDataId + 1);
        assertThat(q2.getId()).isEqualTo(lastSampleDataId + 2);
    }

    @Test
    void test_delete() {
        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);

        Question q = this.questionRepository.findById(1).get();
        questionRepository.delete(q);

        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1);
    }

    @Test
    void test_update() {
        Question q = this.questionRepository.findById(1).get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);

        q = this.questionRepository.findById(1).get();

        assertThat(q.getSubject()).isEqualTo("수정된 제목");
    }

    @Test
    void test_findAll() {
        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(lastSampleDataId);

        Question q = all.get(0);
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    void test_findBySubject() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(q.getId()).isEqualTo(1);
    }


    @Test
    void test_findByContent() {
        Question q = questionRepository.findByContent("sbb에 대해서 알고 싶습니다.");
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void test_findBySubjectAndContent() {
        Question q = questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void test_findBySubjectLike() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);

        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_findAnswerListByQuestionId(){
        Optional<Question> oq = this.questionRepository.findById(lastSampleDataId);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        System.out.println(q.getContent());
        System.out.println(q.getAnswerList());

    }

    @Test
    void createManySampleData() {
        boolean run = false;
        member=memberRepository.findByUsername("user1").get();
        if (run == false) return;

        IntStream.rangeClosed(3, 300).forEach(id -> {
            Question q = new Question();
            q.setSubject("%d번 질문".formatted(id));
            q.setContent("%d번 질문의 내용".formatted(id));
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(member);
            questionRepository.save(q);
        });
    }

}