package site.yejin.sbb.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.yejin.sbb.answer.AnswerCreateForm;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.question.dto.QuestionUpdateForm;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    //@Autowired // 필드 주입
    private final QuestionService questionService;
    private final MemberService memberService;

    @RequestMapping("/questions")
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String subject,
                       @RequestParam(defaultValue = "") String content,
                       @RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "") String answer) {
        Page<Question> paging = questionService.findDistinctBySubjectContainsOrContentContainsOrAuthor_nameContainsOrAnswerList_contentContains(subject, content, name, answer,page);
        model.addAttribute("paging",paging);
        Map<String,String> param = new HashMap<>();
        param.put("subject",subject);
        param.put("content",content);
        param.put("name",subject);
        param.put("answer",subject);
        model.addAttribute("param",param);
        return "question_list";
    }

    @RequestMapping("/questions/{id}")
    public String show(@PathVariable int id, Model model, AnswerCreateForm answerCreateForm, Principal principal){
        Question question = questionService.findById(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/new")
    public String newQuestion(QuestionCreateForm questionCreateForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/questions")
    public String create(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Optional<Integer> oid;

        try {
            log.debug("username : "+ principal.getName());
            log.debug("member : "+ memberService.findByUsername(principal.getName()));
            oid = questionService.create(
                    questionCreateForm.getSubject(),
                    questionCreateForm.getContent(),
                    memberService.findByUsername(principal.getName())
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("createQuestionFailed", "이미 등록된 질문입니다.");
            return "question_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("createQuestionFailed", e.getMessage());
            return "question_form";
        }

        int id=oid.orElseThrow(
                () -> {
                    return new RuntimeException("질문을 등록할 수 없습니다.");
                }
        );

        return "redirect:/questions/%d".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/questions/{id}/edit")
    public String edit(@PathVariable Integer id, QuestionUpdateForm questionUpdateForm, Model model){
        Question question=questionService.findById(id);
        questionUpdateForm.setSubject(question.getSubject());
        questionUpdateForm.setContent(question.getContent());
        model.addAttribute("question",question);
        return "question_update_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/questions/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("questionUpdateForm") QuestionUpdateForm questionUpdateForm, BindingResult bindingResult, Principal principal, Model model){
        Question question=questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question",question);
            return "question_update_form";
        }

        Optional<Integer> oid;

        try {
            log.debug("username : "+ principal.getName());
            log.debug("member : "+ memberService.findByUsername(principal.getName()));
            oid = questionService.update(
                    question,
                    questionUpdateForm.getSubject(),
                    questionUpdateForm.getContent(),
                    memberService.findByUsername(principal.getName())
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("updateQuestionFailed", "이미 등록된 질문입니다.");
            model.addAttribute("question",question);
            return "question_update_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("updateQuestionFailed", e.getMessage());
            model.addAttribute("question",question);
            return "question_update_form";
        }

        return "redirect:/questions/%d".formatted(id);
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable Integer id){
        questionService.delete(id);
        return "redirect:/questions";
    }


}