package com.ao.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ActionExecutor<S> {

    private final ExecutorService processor = Executors.newSingleThreadExecutor();
    private final AtomicInteger pendingJobs = new AtomicInteger(0);

    public final void dispatch(Action<S> action) {
        pendingJobs.incrementAndGet();
        processor.execute(() -> {
            action.performAction(getService());
            pendingJobs.decrementAndGet();
        });
    }

    public int getPendingActionCount() {
        return pendingJobs.intValue();
    }

    protected abstract S getService();

}
