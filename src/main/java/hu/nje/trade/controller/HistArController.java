package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import hu.nje.trade.Utils;
import hu.nje.trade.dto.HistArDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.oanda.v20.instrument.CandlestickGranularity.*;

@Controller
public class HistArController {
    private final Context ctx;

    public HistArController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-histar")
    public String getHistAr(Model model) {
        model.addAttribute("param", new HistArDTO());

        String title = "Forex-HistÁr menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "histar_get";
    }

    @PostMapping("/forex-histar")
    public String postHistAr(@ModelAttribute HistArDTO histArDTO, Model model) {
        StringBuilder strOut;

        try {
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(histArDTO.getInstrument()));
            System.out.printf("request: %s%n", request);

            switch (histArDTO.getGranularity()) {
                case "M1": request.setGranularity(M1); break;
                case "H1": request.setGranularity(H1); break;
                case "D": request.setGranularity(D); break;
                case "W": request.setGranularity(W); break;
                case "M": request.setGranularity(M); break;
                default: throw new IllegalArgumentException("Ismeretlen részletesség: " + histArDTO.getGranularity());
            }

            request.setCount(10L); // utolsó 10 árat kérjünk
            InstrumentCandlesResponse resp = ctx.instrument.candles(request);
            strOut = new StringBuilder();
            for (Candlestick candle : resp.getCandles())
                strOut.append(candle.getTime()).append("\t").append(candle.getMid().getC()).append(";");

            // Időbélyeg kinyerése és formázása olvasható formátumra

            System.out.printf("resp: %s%n", Utils.jsonPrettify(resp));

            model.addAttribute("instrument", histArDTO.getInstrument());
            model.addAttribute("granularity", histArDTO.getGranularity());
            model.addAttribute("price", strOut.toString());
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt az OANDA API lekérés során.");
            e.printStackTrace();
        }

        String title = "Forex-HistÁr menü | Eredmény";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "histar_post";
    }
}
