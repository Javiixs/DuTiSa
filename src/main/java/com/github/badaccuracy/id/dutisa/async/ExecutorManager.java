package com.github.badaccuracy.id.dutisa.async;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ExecutorManager {

    private final Map<String, NExecutor> executorMap = Collections.synchronizedMap(new HashMap<>());

    public NExecutor gocExecutor(String name) {
        if (executorMap.containsKey(name)) {
            return executorMap.get(name);
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        NExecutor nExecutor = new NExecutor() {

            @Override
            public void execute(@NotNull Runnable runnable) {
                executor.execute(runnable);
            }

            @Override
            public void shutdown() {
                executor.shutdown();
            }

            @Override
            public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                executor.awaitTermination(timeout, unit);
            }

            @Override
            public ScheduledFuture<?> schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
                return null;
            }
        };

        executorMap.put(name, nExecutor);
        return nExecutor;
    }

    public NExecutor gocScheduledExecutor(String name) {
        if (executorMap.containsKey(name)) {
            return executorMap.get(name);
        }

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        NExecutor nExecutor = new NExecutor() {

            @Override
            public void execute(@NotNull Runnable runnable) {
                executor.execute(runnable);
            }

            @Override
            public void shutdown() {
                executor.shutdown();
            }

            @Override
            public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                executor.awaitTermination(timeout, unit);
            }

            @Override
            public ScheduledFuture<?> schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
                return executor.scheduleAtFixedRate(runnable, delay, period, timeUnit);
            }
        };

        executorMap.put(name, nExecutor);
        return nExecutor;
    }

    public void shutdown() {
        System.out.println("Shutting down all executors");
        executorMap.values().forEach(it -> {
            try {
                it.shutdown();
                it.awaitTermination(5, TimeUnit.SECONDS);
            } catch (Exception ignored) {
            }
        });
    }

    public interface NExecutor extends Executor {
        void shutdown();

        void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

        ScheduledFuture<?> schedule(Runnable runnable, long delay, long period, TimeUnit timeUnit);
    }
}
