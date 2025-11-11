package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountSummary;
import hu.nje.trade.Config;
import hu.nje.trade.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    private final Context ctx;

    public AccountController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-account")
    public String getAccount(Model model) {
        try {
            AccountSummary summary = ctx.account.summary(Config.ACCOUNTID).getAccount();
            model.addAttribute("accountSummary", summary);
            // System.out.printf("resp: %s%n", Utils.jsonPrettify(summary));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String title = "Account menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "account_get";
    }
}
