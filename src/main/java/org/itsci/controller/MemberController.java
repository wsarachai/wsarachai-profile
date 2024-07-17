package org.itsci.controller;

import org.itsci.controller.bean.MemberBean;
import org.itsci.dao.LoginDao;
import org.itsci.model.*;
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
@RequestMapping("/member")
public class MemberController {
    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    MemberService memberService;

    @Autowired
    private LoginDao loginDao;

    @InitBinder
    public void initBuilder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/change-password")
    public String changePasswordForm(Authentication authentication, Model model) {
        Member member = (Member) authentication.getPrincipal();
        MemberBean memberBean = new MemberBean(member);
        memberBean.setPassword("");
        model.addAttribute("member", memberBean);
        return "user/change-password";
    }

    public boolean checkIfPasswordValid(String password) {
        boolean result = false;
        try {
            if (password != null) {
                String MIN_LENGTH = "8";
                String MAX_LENGTH = "20";
                boolean ONE_DIGIT_NEEDED = false;
                boolean LOWER_CASE_NEEDED = false;
                boolean UPPER_CASE_NEEDED = false;
                boolean SPECIAL_CHAR_NEEDED = false;
                boolean SPACE_NEEDED = false;

                String ONE_DIGIT = ONE_DIGIT_NEEDED ? "(?=.*[0-9])" : "";
                String LOWER_CASE = LOWER_CASE_NEEDED ? "(?=.*[a-z])" : "";
                String UPPER_CASE = UPPER_CASE_NEEDED ? "(?=.*[A-Z])" : "";
                String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
                String NO_SPACE = SPACE_NEEDED ? "(?=\\S+$)" : "";

                String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
                String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;

                return password.matches(PATTERN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping("/change-password")
    public String changePassword(Authentication authentication, Model model, @ModelAttribute("member") MemberBean memberBean) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member user = memberService.getMember(memberBean.getId());
        Login login = user.getLogin();
        model.addAttribute("member", memberBean);

        boolean match = passwordEncoder.matches(memberBean.getPassword(), login.getPassword().replace("{bcrypt}", ""));  // remove {bcrypt} from password
        if (!match) {
            model.addAttribute("success", null);
            model.addAttribute("error", messageSource.getMessage("bean.user.password.wrong", null, Locale.getDefault()));
            return "redirect:/member/change-password";
        }
        if (!memberBean.getNewPassword().equals(memberBean.getConfirmPassword())) {
            model.addAttribute("error", messageSource.getMessage("bean.user.password.notmatch", null, Locale.getDefault()));
            model.addAttribute("success", null);
            return "redirect:/member/change-password";
        }
        if (!checkIfPasswordValid(memberBean.getNewPassword())) {
            model.addAttribute("error", messageSource.getMessage("bean.user.password.invalid", null, Locale.getDefault()));
            model.addAttribute("success", null);
            return "redirect:/member/change-password";
        }

        login.setPassword("{bcrypt}" + passwordEncoder.encode(memberBean.getNewPassword()));

        model.addAttribute("error", null);
        model.addAttribute("success", messageSource.getMessage("bean.user.password.success", null, Locale.getDefault()));
        loginDao.saveOrUpdate(login);
        return "redirect:/member/change-password";
    }

    @GetMapping("/profile")
    public String memberProfile(Authentication authentication, Model model) {
        User currUser = (User) authentication.getPrincipal();
        User user = memberService.getMember(currUser.getId());
        model.addAttribute("member", user);
        model.addAttribute("title", messageSource.getMessage("page.user.profile", null, Locale.getDefault()));
        return "user/profile";
    }

    @GetMapping("/form")
    public String userForm(Authentication authentication, Model model) {
        User currUser = (User) authentication.getPrincipal();
        Member member = memberService.getMember(currUser.getId());
        model.addAttribute("member", member);
        model.addAttribute("title", messageSource.getMessage("page.user.profile", null, Locale.getDefault()));
        return "member/form";
    }

    @PostMapping("/profile")
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

    @GetMapping("/list")
    public String listShop(Model model) {
        model.addAttribute("title", messageSource.getMessage("page.user.list", null, Locale.getDefault()));
        model.addAttribute("members", memberService.getMembers());
        return "member/list";
    }

    @GetMapping("/create")
    public String showFormForAdd(Locale locale, Model model) {
        model.addAttribute("title", messageSource.getMessage("page.user.add", null, Locale.getDefault()));
        model.addAttribute("authorities", EAuthorityType.getAuthorityOptions(messageSource, locale));
        model.addAttribute("members", memberService.getMembers());
        model.addAttribute("member", new Member());
        model.addAttribute("disabled", "false");
        return "member/form";
    }

    @GetMapping("/{id}/update")
    public String showFormForUpdate(@PathVariable("id") int id, Locale locale, Model model) {
        Member member = memberService.getMember(Long.valueOf(id));
        model.addAttribute("title", messageSource.getMessage("page.user.update", null, Locale.getDefault()));
        model.addAttribute("authorities", EAuthorityType.getAuthorityOptions(messageSource, locale));
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
            model.addAttribute("authorities", EAuthorityType.getAuthorities());
            model.addAttribute("member", member);
            model.addAttribute("disabled", "true");
            return "member/form";
        }
        else {
            Member dbMember = memberService.getMember(member.getId());
            if (dbMember == null) {
                dbMember = new Member();
//                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//                String encrypted = bCryptPasswordEncoder.encode(member.getUsername().trim());
//                dbMember.getLogin().setPassword("{bcrypt}" + encrypted);
            }

//            dbMember.getLogin().setUsername(member.getUsername());
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

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return "redirect:/system/member/list";
    }
}
