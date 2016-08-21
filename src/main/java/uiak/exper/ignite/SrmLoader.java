package uiak.exper.ignite;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import uiak.exper.ignite.model.Instrument;
import uiak.exper.ignite.model.SrmKey;

import java.util.stream.IntStream;

public class SrmLoader {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        CacheConfiguration<SrmKey, Instrument> cfg = new CacheConfiguration<>();
        cfg.setCacheMode(CacheMode.PARTITIONED);
        cfg.setName("srmCache");
        load(ignite, cfg);
    }

    private static void load(Ignite ignite, CacheConfiguration<SrmKey, Instrument> cfg) {
        IgniteCache<SrmKey, Instrument> srmCache = ignite.getOrCreateCache(cfg);

        SrmKey s1 = new SrmKey(1, "CUSIP1", "NYSE", "USD");
        Instrument i1 = new Instrument(s1, "MYINST ONE");
        srmCache.put(s1, i1);

        SrmKey s2 = new SrmKey(2, "CUSIP2", "NYSE", "USD");
        Instrument i2 = new Instrument(s2, "MYINST TWO");
        srmCache.put(s2, i2);
    }
}