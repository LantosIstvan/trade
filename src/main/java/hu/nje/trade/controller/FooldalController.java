package hu.nje.trade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooldalController {
    @GetMapping("/")
    public String getFooldal(Model model) {
        model.addAttribute("pageTitle", "Főoldal");
        model.addAttribute("heroMessage", "A közösségi kereskedés új modellje");
        return "fooldal_get";
    }
}
