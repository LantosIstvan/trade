package hu.nje.trade.controller;

import hu.nje.trade.dto.SoapDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage;
import soapclient.MNBArfolyamServiceSoapImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SoapController {
    @GetMapping("/soap")
    public String getSoap(Model model) {
        model.addAttribute("param", new SoapDTO());

        String title = "SOAP menü";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "soap_get";
    }

    @PostMapping("/soap")
    public String postSoap(@ModelAttribute SoapDTO soapDTO, Model model) throws
        MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage
    {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        String mnb = service.getExchangeRates(
            soapDTO.getStartDate(),
            soapDTO.getEndDate(),
            soapDTO.getCurrency()
        );

        try {
            List<Map<String, String>> rates = new ArrayList<>();
            Document doc = Jsoup.parse(mnb, "", Parser.xmlParser());
            Elements days = doc.select("Day");
            for (Element day : days) {
                String date = day.attr("date");
                Element rateEl = day.selectFirst("Rate");
                if (rateEl != null) {
                    String unit = rateEl.attr("unit");
                    String curr = rateEl.attr("curr");
                    String value = rateEl.text();
                    Map<String, String> map = new HashMap<>();
                    map.put("date", date);
                    map.put("unit", unit);
                    map.put("curr", curr);
                    map.put("value", value);
                    rates.add(map);
                }
            }
            model.addAttribute("rates", rates);
            // model.addAttribute("rawXml", mnb); // opcionális: nyers XML megjelenítéshez
        } catch (Exception e) {
            model.addAttribute("error", "XML feldolgozási hiba: " + e.getMessage());
        }

        String title = "SOAP menü | Eredmény";
        model.addAttribute("pageTitle", title);
        model.addAttribute("heroMessage", title);
        return "soap_post";
    }
}
