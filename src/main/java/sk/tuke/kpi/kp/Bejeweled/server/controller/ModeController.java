package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeController {

    @GetMapping("/mode")
    public String chooseMode() {
        return "mode";
    }
}
