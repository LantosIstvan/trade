package hu.nje.trade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    // Thread-safe, így biztonságosan használható statikus mezőként.
    private static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

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
}
