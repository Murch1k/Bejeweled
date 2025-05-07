package sk.tuke.kpi.kp.Bejeweled.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

public class JwtController {
    @GetMapping("/auth/me")
    public ResponseEntity<String> whoAmI(Authentication auth) {
        return ResponseEntity.ok("Hello, " + auth.getName());
    }
}
