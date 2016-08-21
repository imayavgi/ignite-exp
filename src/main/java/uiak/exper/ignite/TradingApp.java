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
import uiak.exper.ignite.service.PriceService;
import uiak.exper.ignite.service.PriceServiceImpl;

public class TradingApp {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        CacheConfiguration<SrmKey, Instrument> cfg = new CacheConfiguration<>();
        cfg.setCacheMode(CacheMode.PARTITIONED);
        cfg.setName("srmCache");
        read(ignite, cfg);
    }

    private static void read(Ignite ignite, CacheConfiguration<SrmKey, Instrument> cfg) {
        IgniteCache<SrmKey, Instrument> srmCache = ignite.getOrCreateCache(cfg);
        srmCache.forEach(e -> {
            SrmKey k = e.getKey();
            Instrument i = e.getValue();

            System.out.println(k.getUiakId() + " = " + i.getShortName() + " price " + i.getLastMktClosePrice());
        });

    }

}
