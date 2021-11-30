package com.graduate.mobilekiosk.web.member;

import com.graduate.mobilekiosk.domain.Member;
import com.graduate.mobilekiosk.web.member.form.LoginDto;
import com.graduate.mobilekiosk.web.member.form.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("sign-up")
    public String sighUpForm(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("member", new MemberSaveDto());
        return "seller/sign-up.html";
    }

    @PostMapping("sign-up")
    public String sighUp(@Validated @ModelAttribute("member") MemberSaveDto memberSaveDto, BindingResult bindingResult) {

        if (memberService.findMember(memberSaveDto.getUsername()) != null) {
            bindingResult.reject("exist", "아이디가 이미 존재합니다.");
        } else if (!memberSaveDto.getPassword().equals(memberSaveDto.getCheckPassword())) {
            bindingResult.reject("differentPassword", "패스워드가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "seller/sign-up.html";
        }

        Member saveMember = Member.builder()
                .userId(memberSaveDto.getUsername())
                .password(memberSaveDto.getPassword())
                .storeName(memberSaveDto.getUrl())
                .role("USER").build();

        memberService.join(saveMember);
        return "redirect:/login?join";
    }

    @GetMapping("/login")
    public String loginform(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("member", new LoginDto());
        return "seller/index.html";
    }

}
