package org.itsci.controller;

import org.itsci.model.Authority;
import org.itsci.model.AuthorityType;
import org.itsci.model.Member;
import org.itsci.model.User;
import org.itsci.service.MemberService;
import org.itsci.utils.UIValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class MemberController {
    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    MemberService memberService;

    @InitBinder
    public void initBuilder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/member/profile")
    public String memberProfile(Authentication authentication, Model model) {
        User currUser = (User) authentication.getPrincipal();
        User user = memberService.getMember(currUser.getId());
        model.addAttribute("member", user);
        model.addAttribute("title", messageSource.getMessage("page.user.profile", null, Locale.getDefault()));
        return "user/profile";
    }

    @GetMapping("/system/user/form")
    public String userForm(Authentication authentication, Model model) {
        User currUser = (User) authentication.getPrincipal();
        Member member = memberService.getMember(currUser.getId());
        model.addAttribute("member", member);
        model.addAttribute("title", messageSource.getMessage("page.user.profile", null, Locale.getDefault()));
        return "member/form";
    }

    @PostMapping("/member/profile")
    public String userSave(@ModelAttribute("member") Member memberFrm,
                           BindingResult bindingResult,
                           Model model,
                           Locale locale,
                           RedirectAttributes redirectAttrs) {
        Member member = memberService.getMember(memberFrm.getId());

        if (!UIValidator.FieldNotNullValidator(memberFrm, "firstName")) {
            bindingResult.rejectValue("firstName", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(memberFrm, "lastName")) {
            bindingResult.rejectValue("lastName", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(memberFrm, "address")) {
            bindingResult.rejectValue("address", "NotNull");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("page.error", null, Locale.getDefault()));
            return "user/profile";
        } else {
            member.setFirstName(memberFrm.getFirstName());
            member.setLastName(memberFrm.getLastName());
            member.setAddress(memberFrm.getAddress());
            memberService.saveMember(member);
            String message = messageSource.getMessage("status.save.success", null, locale);
            redirectAttrs.addFlashAttribute("status", message);
            return "redirect:/";
        }
    }

    @GetMapping("/system/member/list")
    public String listShop(Model model) {
        model.addAttribute("title", messageSource.getMessage("page.user.list", null, Locale.getDefault()));
        model.addAttribute("members", memberService.getMembers());
        return "member/list";
    }

    @GetMapping("/system/member/create")
    public String showFormForAdd(Locale locale, Model model) {
        model.addAttribute("title", messageSource.getMessage("page.user.add", null, Locale.getDefault()));
        model.addAttribute("authorities", AuthorityType.getAuthorityOptions(messageSource, locale));
        model.addAttribute("members", memberService.getMembers());
        model.addAttribute("member", new Member());
        model.addAttribute("disabled", "false");
        return "member/form";
    }

    @GetMapping("/system/member/{id}/update")
    public String showFormForUpdate(@PathVariable("id") int id, Locale locale, Model model) {
        Member member = memberService.getMember(Long.valueOf(id));
        model.addAttribute("title", messageSource.getMessage("page.user.update", null, Locale.getDefault()));
        model.addAttribute("authorities", AuthorityType.getAuthorityOptions(messageSource, locale));
        model.addAttribute("member", member);
        model.addAttribute("disabled", "true");
        return "member/form";
    }

    @RequestMapping(path="/system/member/save", method = RequestMethod.POST)
    public String processForm(@ModelAttribute("member") Member member,
                              BindingResult bindingResult,
                              Model model) {
        List<Authority> authorityToAdd = new ArrayList<>();
        List<Authority> authorityToRemove = new ArrayList<>();

        if (!UIValidator.FieldNotNullValidator(member, "firstName")) {
            bindingResult.rejectValue("firstName", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(member, "lastName")) {
            bindingResult.rejectValue("lastName", "NotNull");
        }
        if (!UIValidator.FieldNotNullValidator(member, "address")) {
            bindingResult.rejectValue("address", "NotNull");
        }
        if (member.getLogin().getAuthorities().size() <= 0) {
            bindingResult.rejectValue("authoritiyOptions", "NotNull");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", messageSource.getMessage("page.error", null, Locale.getDefault()));
            model.addAttribute("authorities", AuthorityType.getAuthorities());
            model.addAttribute("member", member);
            model.addAttribute("disabled", "true");
            return "member/form";
        }
        else {
            Member dbMember = memberService.getMember(member.getId());
            if (dbMember == null) {
                dbMember = new Member();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String encrypted = bCryptPasswordEncoder.encode(member.getUsername().trim());
                dbMember.getLogin().setPassword("{bcrypt}" + encrypted);
            }

            dbMember.getLogin().setUsername(member.getUsername());
            dbMember.setFirstName(member.getFirstName());
            dbMember.setLastName(member.getLastName());
            dbMember.setValidFrom(member.getValidFrom());
            dbMember.setExpiredDate(member.getExpiredDate());
            dbMember.setAddress(member.getAddress());
            dbMember.getLogin().setEnabled(true);

            Set<Authority> authorities = dbMember.getLogin().getAuthorities();
            for (Authority authority : authorities) {
                boolean found = false;
                for (Authority auth : member.getLogin().getAuthorities()) {
                    if (authority.equals(auth)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    authorityToRemove.add(authority);
                }
            }
            for (Authority auth : member.getLogin().getAuthorities()) {
                boolean found = false;
                for (Authority authority : authorities) {
                    if (authority.equals(auth)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    authorityToAdd.add(auth);
                }
            }

            memberService.updateMember(dbMember, authorityToRemove, authorityToAdd);
            return "redirect:/system/member/list";
        }
    }

    @GetMapping("/system/member/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "redirect:/system/member/list";
    }
}
