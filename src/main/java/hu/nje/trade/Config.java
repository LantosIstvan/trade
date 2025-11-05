package hu.nje.trade;

import com.oanda.v20.account.AccountID;
import com.oanda.v20.primitives.InstrumentName;

/**
 * This is the configuration object used by the various examples to connect to
 * one of the OANDA trading systems.  Please fill them in with sane values.
 *
 * @param      URL         The fxTrade or fxPractice API URL
 * @param      TOKEN       The OANDA API Personal Access token
 * @param      ACCOUNTID   A valid v20 trading account ID that {@code TOKEN} has
 *                         permissions to take action on
 * @param      INSTRUMENT  A valid tradeable instrument for the given {@code
 *                         ACCOUNTID}
 */
public class Config {
    private Config() {} // Ne legyen példányosítható

    public static final String URL = "https://api-fxpractice.oanda.com";
    public static final String TOKEN = "aed1690233ddcef16f05d573ad5f2e73-fd2e516429d8ac1a7efc867a3490fe68";
    public static final AccountID ACCOUNTID = new AccountID("101-004-37392287-001");
    // public static final InstrumentName INSTRUMENT  = new InstrumentName("<< INSTRUMENT >>");
}
