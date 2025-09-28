package com.ao.action;

public abstract class Action<S> {

    // TODO Get this injected appropriately for each action type
    private ActionExecutor<S> executor;

    public final void dispatch() {
        if (executor == null) throw new IllegalStateException("Executor not set. Call setExecutor() before dispatch().");
        executor.dispatch(this);
    }

    public void setExecutor(ActionExecutor<S> executor) {
        this.executor = executor;
    }

    protected abstract void performAction(S service);

}
