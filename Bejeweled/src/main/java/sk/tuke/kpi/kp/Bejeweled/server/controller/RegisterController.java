package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
}
