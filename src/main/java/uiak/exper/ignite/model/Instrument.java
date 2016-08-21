package uiak.exper.ignite.model;

import java.math.BigDecimal;

/**
 * Created by imaya on 8/13/16.
 */
public class Instrument {
    private SrmKey srmKey;
    private String shortName;
    private String longName;
    private String issuer;
    private String countryOfDoc;
    private BigDecimal lastMktClosePrice;

    public Instrument(SrmKey srmKey, String shortName) {
        this.srmKey = srmKey;
        this.shortName = shortName;
    }

    public BigDecimal getLastMktClosePrice() {
        return lastMktClosePrice;
    }

    public void setLastMktClosePrice(BigDecimal lastMktClosePrice) {
        this.lastMktClosePrice = lastMktClosePrice;
    }

    public SrmKey getSrmKey() {
        return srmKey;
    }

    public String getShortName() {
        return shortName;
    }


    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCountryOfDoc() {
        return countryOfDoc;
    }

    public void setCountryOfDoc(String countryOfDoc) {
        this.countryOfDoc = countryOfDoc;
    }

}
