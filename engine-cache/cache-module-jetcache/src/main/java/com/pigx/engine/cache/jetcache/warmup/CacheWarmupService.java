package com.pigx.engine.cache.jetcache.warmup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Service for warming up caches on application startup.
 * <p>
 * This service coordinates cache warming operations to pre-populate caches
 * with frequently accessed data, reducing cold-start latency.
 * </p>
 *
 * <p>Cache warmers can be registered and will be executed asynchronously
 * after the application is ready.</p>
 *
 * <p><b>Usage:</b></p>
 * <pre>
 * {@code
 * @Component
 * public class UserCacheWarmer implements CacheWarmer {
 *     @Override
 *     public String getName() { return "UserCache"; }
 *     
 *     @Override
 *     public void warmup() {
 *         // Load frequently accessed users into cache
 *         userService.getActiveUsers();
 *     }
 * }
 * }
 * </pre>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class CacheWarmupService {

    private static final Logger log = LoggerFactory.getLogger(CacheWarmupService.class);

    private final List<CacheWarmer> warmers = new CopyOnWriteArrayList<>();
    private final AtomicBoolean warmupCompleted = new AtomicBoolean(false);
    private final boolean enabled;

    public CacheWarmupService(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Registers a cache warmer to be executed on startup.
     *
     * @param warmer the cache warmer to register
     */
    public void registerWarmer(CacheWarmer warmer) {
        warmers.add(warmer);
        log.debug("[PIGXD] |- Registered cache warmer: [{}]", warmer.getName());
    }

    /**
     * Unregisters a cache warmer.
     *
     * @param warmer the cache warmer to unregister
     */
    public void unregisterWarmer(CacheWarmer warmer) {
        warmers.remove(warmer);
        log.debug("[PIGXD] |- Unregistered cache warmer: [{}]", warmer.getName());
    }

    /**
     * Executes cache warmup when the application is ready.
     * This method runs asynchronously to avoid blocking application startup.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void onApplicationReady() {
        if (!enabled) {
            log.info("[PIGXD] |- Cache warmup is disabled");
            return;
        }

        if (warmers.isEmpty()) {
            log.info("[PIGXD] |- No cache warmers registered, skipping warmup");
            warmupCompleted.set(true);
            return;
        }

        log.info("[PIGXD] |- Starting cache warmup with {} warmers...", warmers.size());
        long startTime = System.currentTimeMillis();

        try {
            // Execute all warmers in parallel
            CompletableFuture<?>[] futures = warmers.stream()
                    .map(this::executeWarmerAsync)
                    .toArray(CompletableFuture[]::new);

            // Wait for all warmers to complete
            CompletableFuture.allOf(futures).join();

            long duration = System.currentTimeMillis() - startTime;
            log.info("[PIGXD] |- Cache warmup completed in {}ms", duration);
            warmupCompleted.set(true);

        } catch (Exception e) {
            log.error("[PIGXD] |- Cache warmup failed: {}", e.getMessage(), e);
        }
    }

    /**
     * Executes a single cache warmer asynchronously.
     */
    private CompletableFuture<Void> executeWarmerAsync(CacheWarmer warmer) {
        return CompletableFuture.runAsync(() -> {
            String warmerName = warmer.getName();
            long startTime = System.currentTimeMillis();
            
            try {
                log.debug("[PIGXD] |- Executing cache warmer: [{}]", warmerName);
                warmer.warmup();
                long duration = System.currentTimeMillis() - startTime;
                log.info("[PIGXD] |- Cache warmer [{}] completed in {}ms", warmerName, duration);
            } catch (Exception e) {
                log.error("[PIGXD] |- Cache warmer [{}] failed: {}", warmerName, e.getMessage(), e);
            }
        });
    }

    /**
     * Triggers manual cache warmup.
     * Useful for refreshing caches after data changes.
     */
    public void triggerWarmup() {
        log.info("[PIGXD] |- Triggering manual cache warmup");
        warmupCompleted.set(false);
        onApplicationReady();
    }

    /**
     * Checks if cache warmup has completed.
     *
     * @return true if warmup is complete
     */
    public boolean isWarmupCompleted() {
        return warmupCompleted.get();
    }

    /**
     * Gets the number of registered warmers.
     *
     * @return warmer count
     */
    public int getWarmerCount() {
        return warmers.size();
    }

    /**
     * Interface for cache warmers.
     * Implement this interface to create custom cache warmers.
     */
    public interface CacheWarmer {
        
        /**
         * Gets the name of this cache warmer for logging purposes.
         *
         * @return the warmer name
         */
        String getName();

        /**
         * Performs the cache warmup operation.
         * This method should pre-load data into caches.
         */
        void warmup();

        /**
         * Gets the priority of this warmer (lower = higher priority).
         * Default is 100.
         *
         * @return the priority value
         */
        default int getPriority() {
            return 100;
        }
    }
}
