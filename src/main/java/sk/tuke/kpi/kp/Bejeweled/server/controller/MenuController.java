package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MenuController {

    @GetMapping("/")
    public String menu(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "menu";
    }

    @GetMapping("/rules")
    public String rules() {
        return "rules";
    }
}