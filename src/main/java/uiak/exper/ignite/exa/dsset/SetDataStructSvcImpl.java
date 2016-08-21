package uiak.exper.ignite.exa.dsset;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.IgniteSet;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;

import java.util.UUID;

import static org.apache.ignite.cache.CacheAtomicityMode.TRANSACTIONAL;
import static org.apache.ignite.cache.CacheMode.PARTITIONED;

public class SetDataStructSvcImpl implements SetDataStructSvc {

    private IgniteSet<String> set;
    private String setName = "KICK_FAM_SET";
    private Ignite ignite = null;

    @Override
    public void initialize() throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        ignite = Ignition.start(icfg);
        //setName = UUID.randomUUID().toString();
        initializeSet(ignite, setName);
    }

    private void initializeSet(Ignite ignite, String setName) throws IgniteException {
        CollectionConfiguration setCfg = new CollectionConfiguration();

        setCfg.setAtomicityMode(TRANSACTIONAL);
        setCfg.setCacheMode(PARTITIONED);
        setCfg.setBackups(1);

        // Initialize new set.
        set = ignite.set(setName, setCfg);
    }

    @Override
    public void writeToSet() throws IgniteException {
        // Initialize set items.
        for (int i = 0; i < 10; i++)
            set.add(Integer.toString(i));

        final String setName = set.name();
        ignite.compute().broadcast(new SetClosure(setName));

        System.out.println("Set size after initializing and writing: " + set.size());
    }

    @Override
    public void readAndTestSet() throws IgniteException {
        System.out.println("Set size after writing [expected=" + (10 + ignite.cluster().forServers().nodes().size() * 5) +
            ", actual=" + set.size() + ']');

        System.out.println("Iterate over set.");

        // Iterate over set.
        for (String item : set)
            System.out.println("Set item: " + item);

        // Set API usage examples.
        if (!set.contains("0"))
            throw new RuntimeException("Set should contain '0' among its elements.");

        if (set.add("0"))
            throw new RuntimeException("Set should not allow duplicates.");

        if (!set.remove("0"))
            throw new RuntimeException("Set should correctly remove elements.");

        if (set.contains("0"))
            throw new RuntimeException("Set should not contain '0' among its elements.");

        if (!set.add("0"))
            throw new RuntimeException("Set should correctly add new elements.");
    }

    @Override
    public void clearAndRemoveSet() throws IgniteException {
        System.out.println("Set size before clearing: " + set.size());

        // Clear set.
        set.clear();

        System.out.println("Set size after clearing: " + set.size());

        // Remove set.
        set.close();

        System.out.println("Set was removed: " + set.removed());

        // Try to work with removed set.
        try {
            set.contains("1");
        }
        catch (IllegalStateException expected) {
            System.out.println("Expected exception - " + expected.getMessage());
        }
    }

     private static class SetClosure implements IgniteRunnable {
        /** Set name. */
        private final String setName;

        /**
         * @param setName Set name.
         */
        SetClosure(String setName) {
            this.setName = setName;
        }

        /** {@inheritDoc} */
        @Override public void run() {
            IgniteSet<String> set = Ignition.ignite().set(setName, null);

            UUID locId = Ignition.ignite().cluster().localNode().id();

            for (int i = 0; i < 5; i++) {
                String item = locId + "_" + Integer.toString(i);

                set.add(item);

                System.out.println("Set item has been added: " + item);
            }
        }
    }
}