package com.example.wof;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public RedirectView redirectSwaggerUi() {
        return new RedirectView("/swagger-ui/index.html ", true);
    }
}