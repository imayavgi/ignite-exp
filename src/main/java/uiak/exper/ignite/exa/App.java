package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        CacheConfiguration<Integer, String> cfg = new CacheConfiguration<>();
        cfg.setCacheMode(CacheMode.PARTITIONED);
        cfg.setName("myCache");
        hw(ignite, cfg);
        /*
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            CacheConfiguration<Integer, String> cfg = new CacheConfiguration<>();
            cfg.setCacheMode(CacheMode.PARTITIONED);
            cfg.setName("myCache");
            hw(ignite, cfg);
        }
        */
    }

    private static void hw(Ignite ignite, CacheConfiguration<Integer, String> cfg) {
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);
        IntStream.range(10, 50).forEach(i -> {
            cache.put(i, "VAL" + i);
            /*
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        });
    }
}