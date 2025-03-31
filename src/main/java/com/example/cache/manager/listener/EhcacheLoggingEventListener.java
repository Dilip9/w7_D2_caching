//package com.example.cache.manager.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.ehcache.event.CacheEvent;
//import org.ehcache.event.CacheEventListener;
//
//@Slf4j
//public class EhcacheLoggingEventListener implements CacheEventListener<Object, Object> {
//    @Override
//    public void onEvent(CacheEvent<?, ?> cacheEvent) {
//        log.info("key {} | event {}  | oldValue {}  | newValue {} ", cacheEvent.getKey(),
//                cacheEvent.getType(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
//
//    }
//}
