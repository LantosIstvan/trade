package hu.nje.trade.controller;

import com.oanda.v20.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooldalController {
    private final Context ctx;

    public FooldalController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/")
    public String getFooldal(Model model) {
        // Oanda ctx használható itt, más menüpontokban lehet hasznos

        model.addAttribute("pageTitle", "Főoldal");
        model.addAttribute("welcomeMessage", "A new model for open collaboration");
        return "fooldal";
    }
}
