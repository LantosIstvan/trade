package hu.nje.trade.controller;

import hu.nje.trade.Config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {
    @GetMapping("/api-test")
    public String checkConfigValues() {
        String response = "<h1>Konfigurációs értékek ellenőrzése</h1>" +
            "<p><b>URL:</b> " + Config.URL + "</p>" +
            "<p><b>Account ID:</b> " + Config.ACCOUNTID + "</p>" +
            "<p><b>Token:</b> " + Config.TOKEN + "</p>";
        return response;
    }
}
