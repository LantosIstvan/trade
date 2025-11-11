package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import hu.nje.trade.Config;
import hu.nje.trade.dto.ZarDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ZarController {
    private final Context ctx;

    public ZarController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-zar")
    public String getZar(Model model) {
        model.addAttribute("param", new ZarDTO());

        String title = "Zárás menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "zar_get";
    }

    @PostMapping("/forex-zar")
    public String postZar(@ModelAttribute ZarDTO zarDTO, Model model) {
        String tradeId = zarDTO.getTradeId() + "";
        try {
            ctx.trade.close(new TradeCloseRequest(Config.ACCOUNTID, new TradeSpecifier(tradeId)));
            model.addAttribute("tradeId", tradeId);
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt az OANDA API lekérése során.");
            e.printStackTrace();
        }

        String title = "Zárás menü | Eredmény";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "zar_post";
    }

}
