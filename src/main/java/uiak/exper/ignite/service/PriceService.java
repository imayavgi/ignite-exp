package uiak.exper.ignite.service;

import javax.cache.CacheException;

/**
 * Created by imaya on 8/14/16.
 */
public interface PriceService {
    void updatePrice() throws CacheException;
}
