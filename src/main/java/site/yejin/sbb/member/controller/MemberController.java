package site.yejin.sbb.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.yejin.sbb.global.exception.SignupEmailDuplicatedException;
import site.yejin.sbb.global.exception.SignupUsernameDuplicatedException;
import site.yejin.sbb.member.dto.MemberDto;
import site.yejin.sbb.member.dto.MemberUpdateDto;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.service.MemberService;

import javax.validation.Valid;
import java.security.Principal;


@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MemberController {

    private final MemberService memberService;
    private PasswordEncoder passwordEncoder;
    @GetMapping("/signup")
    public String signup(MemberDto memberDto) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!memberDto.getPassword1().equals(memberDto.getPassword2())) {
            bindingResult.rejectValue("memberPwd2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            memberService.create(
                    memberDto.getUsername(),
                    memberDto.getPassword1(),
                    memberDto.getName(),
                    memberDto.getEmail()
            );
        } catch (SignupUsernameDuplicatedException e) {
            e.printStackTrace();
            bindingResult.reject("signupUsernameDuplicated", e.getMessage());
            return "signup_form";
        } catch (SignupEmailDuplicatedException e) {
            e.printStackTrace();
            bindingResult.reject("signupEmailDuplicated", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(MemberDto memberDto) {
        return "login_form";
    }

    @GetMapping("/members")
    public String detail(Model model, Principal principal){
        Member member = memberService.findByUsername(principal.getName());
        model.addAttribute("member",member);
        return "member_detail";
    }

    @DeleteMapping("/members/{id}")
    public String delete(@PathVariable long id){
        memberService.delete(id);
        return "redirect:/logout";
    }
    @GetMapping("/members/{id}")
    public String modify(@PathVariable("id") Long id, Model model, MemberUpdateDto memberUpdateDto) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "member_form";
    }
    @PutMapping("/members/{id}")
    public String update(@PathVariable Long id, Member newMember, @Valid @ModelAttribute("memberUpdateDto") MemberUpdateDto memberUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member_form";
        }

        System.out.println("pw1 : " + memberUpdateDto.getPassword1() + " pw2 : " + memberUpdateDto.getPassword2());
        if (!memberUpdateDto.getPassword1().equals(memberUpdateDto.getPassword2())) {
            bindingResult.rejectValue("memberPwd2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "member_form";
        }
        Member orgMember = memberService.findById(id);
        memberService.update(orgMember,newMember);
        return "redirect:/";
    }

}