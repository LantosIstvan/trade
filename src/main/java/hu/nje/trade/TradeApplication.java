package hu.nje.trade;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TradeApplication {
    @Bean
    public Context oandaContext() {
        return new ContextBuilder(Config.URL)
            .setToken(Config.TOKEN)
            .setApplication("TradeApp")
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }
}
