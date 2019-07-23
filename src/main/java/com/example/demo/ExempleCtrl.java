package com.example.demo;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExempleCtrl {

    // GET http://localhost:8080/exemples
    @GetMapping("/exemples")
    public String get() {
        return "ceci est un exemple de GET";
    }

    // POST http://localhost:8080/exemples
    @PostMapping("/exemples")
    public String post() {
        return "ceci est un exemple de POST";
    }

    // TODO
    @GetMapping("/admin")
    public String getAdmin() {
        return "ceci est un exemple de GET /admin (role ADMIN)";
    }

    // TODO
    @Secured("ROLE_ADMIN")
    @GetMapping("/dba")
    public String getDBA() {
        return "ceci est un exemple de GET /dba (rôle ADMIN)";
    }

}
