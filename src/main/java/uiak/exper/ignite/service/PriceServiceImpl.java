package uiak.exper.ignite.service;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import uiak.exper.ignite.model.Instrument;
import uiak.exper.ignite.model.SrmKey;

import javax.cache.CacheException;
import java.math.BigDecimal;

/**
 * Created by imaya on 8/14/16.
 */
public class PriceServiceImpl implements Service, PriceService {

    private static final long serialVersionUID = 0L;

    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<SrmKey, Instrument> srmCache;

    private String svcName;

    @Override
    public void updatePrice() throws CacheException {
        if (srmCache != null ) {
            srmCache.forEach(e -> {
                SrmKey k = e.getKey();
                Instrument i = e.getValue();
                i.setLastMktClosePrice(new BigDecimal(10.00));
                srmCache.replace(k, i);
            });
        }
    }

    @Override
    public void cancel(ServiceContext serviceContext) {
        System.out.println(" Service cancelled " + svcName);
    }

    @Override
    public void init(ServiceContext serviceContext) throws Exception {
        srmCache = ignite.cache("srmCache");
        svcName = serviceContext.name();
        System.out.println(" Service Initialized " + svcName);
    }

    @Override
    public void execute(ServiceContext serviceContext) throws Exception {
        System.out.println(" Service execution started " + svcName);
    }
}
