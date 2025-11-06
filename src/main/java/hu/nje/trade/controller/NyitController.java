package hu.nje.trade.controller;

import com.oanda.v20.Context;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.pricing_common.PriceValue;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.transaction.OrderFillTransaction;
import hu.nje.trade.Config;
import hu.nje.trade.dto.NyitDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NyitController {
    private final Context ctx;

    public NyitController(Context ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/forex-nyit")
    public String getNyit(Model model) {
        model.addAttribute("param", new NyitDTO());

        String title = "Forex-Nyit menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "nyit_get";
    }

    @PostMapping("/forex-nyit")
    public String postNyit(@ModelAttribute NyitDTO nyitDTO, Model model) {
        try {
            InstrumentName instrument = new InstrumentName(nyitDTO.getInstrument());
            OrderCreateRequest request = new OrderCreateRequest(Config.ACCOUNTID);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(instrument);
            marketorderrequest.setUnits(nyitDTO.getUnits());
            request.setOrder(marketorderrequest);
            OrderCreateResponse response = ctx.order.create(request);

            OrderFillTransaction fillTransaction = response.getOrderFillTransaction();
            PriceValue price = fillTransaction.getPrice();
            int units = nyitDTO.getUnits();

            // Kalkuláció a pozíció értékére
            // A PriceValue-t double értékké konvertáljuk a számításhoz
            double priceAsDouble = price.doubleValue();
            double totalValue = units * priceAsDouble;

            model.addAttribute("instrument", nyitDTO.getInstrument());
            model.addAttribute("units", units);
            model.addAttribute("price", price.toString());
            model.addAttribute("totalValue", String.format("%.2f", totalValue));
            model.addAttribute("id", fillTransaction.getId());
        } catch (Exception e) {
            model.addAttribute("error", "Hiba történt az OANDA API lekérése során.");
            e.printStackTrace();
        }

        String title = "Forex-Nyit menü | Eredmény";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "nyit_post";
    }
}
