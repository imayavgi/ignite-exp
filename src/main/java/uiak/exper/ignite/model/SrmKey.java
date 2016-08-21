package uiak.exper.ignite.model;

/**
 * Created by imaya on 8/13/16.
 */
public class SrmKey {
    private int uiakId;
    private String cusip;
    private String exchange;
    private String currency;

    public SrmKey(int uiakId, String cusip, String exchange, String currency) {
        this.uiakId = uiakId;
        this.cusip = cusip;
        this.exchange = exchange;
        this.currency = currency;
    }

    public int getUiakId() {
        return uiakId;
    }

    public String getCusip() {
        return cusip;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if ( ((SrmKey)o).getUiakId() == this.getUiakId() ) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return this.getUiakId();
    }
}
