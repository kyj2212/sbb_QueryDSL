package site.yejin.sbb.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.yejin.sbb.answer.Answer;
import site.yejin.sbb.answer.AnswerCreateForm;
import site.yejin.sbb.answer.service.AnswerService;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.member.service.MemberUserDetailService;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionService;
import site.yejin.sbb.question.dto.QuestionUpdateForm;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Optional;

@RequestMapping("/answers")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    //@Autowired // 필드 주입
    private final AnswerService answerService;
    private final QuestionService questionService;

    private final MemberService memberService;
    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String create(@PathVariable int id, Model model, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, Principal principal) {
        Question question= this.questionService.findById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        Member member = memberService.findByUsername(principal.getName());
        this.answerService.create(question,answerCreateForm.getContent(), member);
        return String.format("redirect:/questions/%s",id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, AnswerCreateForm answerCreateForm, Model model, BindingResult bindingResult, Principal principal){
        Answer answer = answerService.findById(id);
        Question question=questionService.findById(answer.getQuestion().getId());
        answerCreateForm.setContent(answer.getContent());
        model.addAttribute("question",question);
        model.addAttribute("answer",answer);
        return "question_update_detail";
    }
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String update(@PathVariable int id, Model model, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, Principal principal) {
        Answer answer = answerService.findById(id);
        Question question = answer.getQuestion();
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.update(answer,answerCreateForm.getContent());
        return String.format("redirect:/questions/%s",question.getId());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Answer answer= answerService.findById(id);
        answerService.delete(answer);
        return String.format("redirect:/questions/%s",answer.getQuestion().getId());
    }


}