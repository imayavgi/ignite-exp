package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public class ConsumerApp {
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            hw(ignite);
        }
    }


    private static void hw(Ignite ignite) {
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
        IntStream.range(10,50).forEach(i -> {
                    System.out.println(" From Cache for " + i  + " Value = "+ cache.get(i));
                });

        //ignite.compute().broadcast(() -> System.out.println(Thread.currentThread().getId()+cache.get(1) + " " + cache.get(2)));
    }
}