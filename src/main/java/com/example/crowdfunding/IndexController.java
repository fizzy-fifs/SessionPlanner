package com.example.crowdfunding;

import io.swagger.models.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        return "redirect: /api/swagger-ui.html";
    }
}
