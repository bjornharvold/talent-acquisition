package com.tps.tpi.cache;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import com.tps.tpi.AbstractTest;
import com.tps.tpi.exception.CacheException;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Bjorn Harvold
 * Date: Nov 13, 2009
 * Time: 11:08:50 PM
 * Responsibility:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class CacheManagerTest extends AbstractTest {
    private final static Logger log = LoggerFactory.getLogger(CacheManagerTest.class);
    private final static String KEY = "thekey";
    private final static String VALUE = "thevalue";
    private final static String[] groups = new String[]{"group"};

    @Test
    public void testCache() {
        try {
            log.info("Testing cache manager...");

            log.info("Trying to retrieve object from cache using key: " + KEY);
            log.info("Since we haven't put anything in the cache yet we should get a big fat NULL");
            Object obj = cacheManager.getFromCache(KEY, String.class);
            assertNull("There should be no object in cache with a key: " + KEY, obj);
            log.info("All good. Now we put a value in the cache for key: " + KEY);

            cacheManager.putInCache(groups, KEY, VALUE);

            log.info("Ok done. Now we try to retrieve it again with the same key: " + KEY);
            obj = cacheManager.getFromCache(KEY, String.class);
            assertNotNull("There should be an object in the cache with a key: " + KEY, obj);
            assertEquals("The value we put in the cache doesn't match", VALUE, obj);
            log.info("Retrieved value from cache successfully");

            log.info("Now we want to flush the cache and try to retrieve the value again. This time it should be NULL again.");
            cacheManager.flush(groups);
            log.info("Flush complete. Time to retrieve the value");

            obj = cacheManager.getFromCache(KEY, String.class);
            assertNull("There should be no object in cache any longer with a key: " + KEY, obj);
            log.info("Ok, value has been removed from the cache again successfully");

        } catch (CacheException e) {
            log.error(e.getMessage(), e);
            fail();
        }
    }

    @Autowired
    private CacheManager cacheManager;
}
