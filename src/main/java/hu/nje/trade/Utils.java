package hu.nje.trade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oanda.v20.primitives.DateTime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
    // Thread-safe, így biztonságosan használható statikus mezőként
    private static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

    // A DateTimeFormatter szintén thread-safe
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
        .withZone(ZoneId.systemDefault());

    private Utils() {
        throw new UnsupportedOperationException("Ez egy segédosztály, nem példányosítható.");
    }

    /**
     * Bármilyen Java objektumot olvasható, formázott JSON Stringgé alakít.
     *
     * @param object A szerializálandó objektum.
     * @return Az objektum formázott JSON reprezentációja String-ként.
     */
    public static String jsonPrettify(Object object) {
        if (object == null) return "null"; // A JSON specifikációnak megfelelő null érték
        return GSON_PRETTY.toJson(object);
    }

    /**
     * Egy OANDA V20 API-ból származó DateTime objektumot alakít át olvasható,
     * a rendszer alapértelmezett időzónájának megfelelő formátumra.
     *
     * @param oandaDateTime A formázandó, {@link com.oanda.v20.primitives.DateTime} típusú objektum.
     * @return A formázott dátum és idő String-ként, vagy hibaüzenet, ha a bemenet érvénytelen.
     */
    public static String formatTimestamp(DateTime oandaDateTime) {
        if (oandaDateTime == null) return "Ismeretlen időpont";
        try {
            // Az oandaDateTime.toString() ISO 8601 Stringet ad vissza.
            // Ezt alakítjuk át modern java.time.Instant objektummá.
            Instant timeInstant = Instant.parse(oandaDateTime.toString());
            return DATE_TIME_FORMATTER.format(timeInstant);
        } catch (DateTimeParseException e) {
            System.err.println("Hiba az OANDA DateTime objektum formázásakor: " + oandaDateTime);
            return "Érvénytelen időformátum";
        }
    }
}
