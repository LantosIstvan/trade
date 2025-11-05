package hu.nje.trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateTestController {
    @GetMapping("/template-test")
    public String templateTest(Model model) {
        model.addAttribute("pageTitle", "A new model for <br> open collaboration");
        return "fooldal";
    }
}
