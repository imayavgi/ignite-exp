package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

import java.util.stream.IntStream;

public class BroadcastApp {
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            bc(ignite);
        }
    }


    private static void bc(Ignite ignite) {
        IgniteCache<Integer, String> cache = ignite.cache("myCache");
        IntStream.range(10, 50).forEach(i -> ignite.compute().affinityRun("myCache", i, () -> {
                    System.out.println("Running on " + Thread.currentThread().getId());
                    cache.putIfAbsent(i,"LOCALVAL" + i);
                    if (i % 2 == 0 )
                        cache.put(i,"UPDATED" + i);
                    //System.out.println(Thread.currentThread().getId() + " From Cache for " + i + "Value = " + cache.localPeek(i))
                }
        ));
    }
}