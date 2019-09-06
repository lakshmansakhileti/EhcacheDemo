package com.demo;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheDemo {

    static final Employee emp1 = new Employee(1);
    static final Employee emp2 = new Employee(2);
    private static final Logger log = LoggerFactory.getLogger(EhCacheDemo.class);

    public static void main(String[] args) throws InterruptedException {
        //1. Create a cache manager
        CacheManager cm = CacheManager.getInstance();

        //2. Create caches
        cm.addCache("cache1");
        cm.addCache("cache2");

        //3. make cache1 as eternal
        Cache cache1 = cm.getCache("cache1");
        cache1.getCacheConfiguration().setEternal(true);

        //4. Put in cache emp1 as key & value both
        cache1.put(new Element(emp1, emp1));

        //3. Make cache2 TTL as 1 second
        Cache cache2 = cm.getCache("cache2");
        cache2.getCacheConfiguration().setTimeToLiveSeconds(1);

        //4. Put same object in cache2
        cache2.put(new Element(emp1, emp1));


        //5. Get element from caches
        System.out.println("Getting from Cache1 " + get(cache1, emp1));
        System.out.println("Getting from Cache2 " + get(cache2, emp1));

//      cache2.removeAll();
//       cache2.put(new Element(emp1, emp2));

        //wait for 3 seconds so that element expires in cache2
        Thread.sleep(3000);
        log.info("\nWaiting for cache2 to expire\n");

        //5. Get element from caches again
        log.info("Getting from Cache1 " + get(cache1, emp1));
        log.info("Getting from Cache2 " + get(cache2, emp1));


        log.info("Is element in Cache1 " + cache1.isKeyInCache(emp1));
        log.info("Is element in Cache2 " + cache2.isKeyInCache(emp1));

        log.info("\nRemoving cache1\n");
        cm.removeCache("cache1");

        try {
            log.info(get(cache1, emp1).toString());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        //8. shut down the cache manager
        cm.shutdown();
    }

    static Employee get(Cache cache, Employee key){
        Element ele1 = cache.get(emp1);
        return ele1 == null ? null : (Employee)(ele1.getObjectValue());
    }
}
