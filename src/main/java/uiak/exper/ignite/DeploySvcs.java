package uiak.exper.ignite;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import uiak.exper.ignite.service.PriceService;
import uiak.exper.ignite.service.PriceServiceImpl;

public class DeploySvcs {
    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration icfg = new IgniteConfiguration();
        icfg.setClientMode(true);
        icfg.setPeerClassLoadingEnabled(true);
        Ignite ignite = Ignition.start(icfg);
        deployPriceService(ignite);
    }

    private static void deployPriceService(Ignite ig) {
        if ( ig.cluster().forServers().nodes().isEmpty()) {
            System.out.println(" No Server Nodes");
            return;
        }

        ig.services(ig.cluster().forServers()).deployClusterSingleton("priceSvcClusterSingleton", new PriceServiceImpl());
        execPriceService(ig);
    }

    private static void execPriceService(Ignite ig) {
        PriceService svc = ig.services().serviceProxy("priceSvcClusterSingleton", PriceService.class, false);
        if ( svc != null)
            svc.updatePrice();
        else
            System.out.println(" Could not get service");
    }
}
