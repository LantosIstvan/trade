package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.trade.Trade;
import hu.nje.trade.Config;
import hu.nje.trade.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class PozController {
    private final Context ctx;

    public PozController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-poz")
    public String getPoz(Model model) {
        try {
            List<Trade> trades = ctx.trade.listOpen(Config.ACCOUNTID).getTrades();
            model.addAttribute("trades", trades);
            // System.out.printf("resp: %s%n", Utils.jsonPrettify(trades));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String title = "Pozíció menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "poz_get";
    }
}
