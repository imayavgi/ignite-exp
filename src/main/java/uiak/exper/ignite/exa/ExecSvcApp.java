package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecSvcApp {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        dc(ignite);
    }

    private static void dc(Ignite ignite) {
        ExecutorService exec = ignite.executorService();

        // Iterate through all words in the sentence and create callable jobs.
        for (final String word : "Print words using runnable".split(" ")) {
            // Execute runnable on some node.
            exec.submit((IgniteRunnable) () -> {
                System.out.println();
                System.out.println(">>> Printing '" + word + "' on this node from ignite job.");
            });
        }

        exec.shutdown();

        // Wait for all jobs to complete (0 means no limit).
        try {
            exec.awaitTermination(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(">>> Check all nodes for output (this node is also part of the cluster).");
    }

}