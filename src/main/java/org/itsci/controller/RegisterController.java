package org.itsci.controller;

import org.itsci.model.Member;
import org.itsci.service.MemberService;
import org.itsci.utils.UIValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.Locale;

@Controller
public class RegisterController {
    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String showFormForRegister(Model model) {
        model.addAttribute("title", messageSource.getMessage("page.register.title", null, Locale.getDefault()));
        model.addAttribute("member", new Member());
        return "register/register-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("member") Member member,
                           BindingResult bindingResult,
                           Model model) {
        if (!UIValidator.FieldNotNullValidator(member, "username")) {
            bindingResult.rejectValue("username", "NotNull");
        } else if (!UIValidator.FieldPatternValidator(member, "username")) {
            bindingResult.rejectValue("username", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(member, "password")) {
            bindingResult.rejectValue("password", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(member, "confirmPassword")) {
            bindingResult.rejectValue("confirmPassword", "NotNull");
        } else if (!UIValidator.FieldsValueMatchValidator(member, "password", "confirmPassword")) {
            bindingResult.rejectValue("confirmPassword", "MisMatch");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("page.error", null, Locale.getDefault()));
            return "register/register-form";
        } else {
            member.setValidFrom(new Date());
            memberService.register(member);
            return "redirect:/";
        }
    }
}
