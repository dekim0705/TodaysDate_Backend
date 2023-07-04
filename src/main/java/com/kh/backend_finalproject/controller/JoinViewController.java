package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/join")
public class JoinViewController {
    @Autowired
    UserService userService;

    @GetMapping("/auth")
    public String authenticateUser(@RequestParam("email") String email, @RequestParam("authKey") String authKey, Model model) {
            try {
                userService.checkEmailWithAuthKey(email, authKey);
                System.out.println("🍒 이메일 인증 완료: " + email);

                model.addAttribute("message", "이메일 인증이 완료되었습니다!");
                return "auth";

            } catch (IllegalArgumentException e) {
                System.out.println("🍒 실패! 인증키가 올바르지 않습니다 : " + email);
                System.out.println("Exception message: " + e.getMessage());

                model.addAttribute("message", "이메일 인증에 실패하였습니다. 고객센터에 문의해 주세요. : devpawcommunity@naver.com");
                return "error";
            }
        }
}
