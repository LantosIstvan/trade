package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import hu.nje.trade.Config;
import hu.nje.trade.Utils;
import hu.nje.trade.dto.AktArDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        model.addAttribute("param", new AktArDTO());

        String title = "Forex-AktÁr menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "aktar_get";
    }

    @PostMapping("/forex-aktar")
    public String postAktAr(@ModelAttribute AktArDTO aktArDTO, Model model) {
        List<String> instruments = new ArrayList<>();
        instruments.add(aktArDTO.getInstrument());
        // System.out.printf("Instruments: %s%n", instruments.getFirst());

        try {
            PricingGetRequest request = new PricingGetRequest(Config.ACCOUNTID, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);
            // System.out.printf("resp: %s%n", Utils.jsonPrettify(resp));

            // Az első (és valószínűleg egyetlen) árfolyamobjektumot használjuk
            ClientPrice price = resp.getPrices().getFirst();
            // System.out.printf("resp.getPrices(): %s%n", Utils.jsonPrettify(resp.getPrices()));

            // Legjobb vételi (Bid) és eladási (Ask) ár kinyerése (lista első eleme, ha nem üres)
            String bestBid = !price.getBids().isEmpty() ? price.getBids().getFirst().getPrice().toString() : "N/A";
            String bestAsk = !price.getAsks().isEmpty() ? price.getAsks().getFirst().getPrice().toString() : "N/A";
            // System.out.printf("price.getBids(): %s%n", Utils.jsonPrettify(price.getBids()));

            // Időbélyeg kinyerése és formázása olvasható formátumra
            String formattedTime = Utils.formatTimestamp(price.getTime());

            // Adatok átadása a modellnek
            model.addAttribute("instrument", price.getInstrument().toString());
            model.addAttribute("bidPrice", bestBid);
            model.addAttribute("askPrice", bestAsk);
            model.addAttribute("timestamp", formattedTime);
        } catch (Exception e) {
            // System.err.println("A dobott kivétel valódi típusa: " + e.getClass().getName());
            model.addAttribute("error", "Hiba történt az OANDA API lekérés során.");
            e.printStackTrace();
        }

        String title = "Forex-AktÁr menü | Eredmény";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "aktar_post";
    }
}
