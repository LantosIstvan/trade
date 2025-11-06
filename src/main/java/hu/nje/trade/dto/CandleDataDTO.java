package hu.nje.trade.dto;

/**
 * Egyetlen gyertya (candlestick) megjelenítésre szánt adatait tároló DTO.
 */
public class CandleDataDTO {
    private final String formattedTime;
    private final String price;

    public CandleDataDTO(String formattedTime, String price) {
        this.formattedTime = formattedTime;
        this.price = price;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public String getPrice() {
        return price;
    }
}
