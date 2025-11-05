package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import hu.nje.trade.Config;
import hu.nje.trade.dto.AktArDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AktArController {
    private final Context ctx;

    public AktArController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-aktar")
    public String getAktAr(Model model) {
        model.addAttribute("pageTitle", "Forex-AktÁr menü | GET");
        model.addAttribute("par", new AktArDTO());
        return "aktar_get";
    }

    @PostMapping("/forex-aktar")
    public String postAktAr(@ModelAttribute AktArDTO aktArDTO, Model model) {
        StringBuilder strOut = new StringBuilder();
        List<String> instruments = new ArrayList<>();
        instruments.add(aktArDTO.getInstrument());
        try {
            PricingGetRequest request = new PricingGetRequest(Config.ACCOUNTID, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);
            for (ClientPrice price : resp.getPrices()) strOut.append(price).append("<br>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("pageTitle", "Forex-AktÁr menü | POST");
        model.addAttribute("instr", aktArDTO.getInstrument());
        model.addAttribute("price", strOut.toString());
        return "aktar_post";
    }
}
