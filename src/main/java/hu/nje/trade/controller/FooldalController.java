package hu.nje.trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooldalController {
    @GetMapping("/")
    public String templateTest(Model model) {
        model.addAttribute("pageTitle", "Főoldal");
        model.addAttribute("welcomeMessage", "A new model for open collaboration");
        return "fooldal";
    }
}
