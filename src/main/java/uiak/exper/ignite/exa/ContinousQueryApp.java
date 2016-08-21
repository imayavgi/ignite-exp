package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.IgniteConfiguration;

import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEventFilter;

public class ContinousQueryApp {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
        // Create new continuous query.
        ContinuousQuery<Integer, String> qry = new ContinuousQuery<>();

        qry.setInitialQuery(new ScanQuery<>((k, v) -> k > 10));

        qry.setLocalListener(evts -> evts.forEach(e -> System.out.println("Changed key=" + e.getKey() + ", val=" + e.getValue())));

        qry.setRemoteFilterFactory((Factory<CacheEntryEventFilter<Integer, String>>) () -> e -> e.getKey() > 10);

        cache.query(qry).forEach(e -> System.out.println("Got key=" + e.getKey() + ", Got val=" + e.getValue()));

    }
}