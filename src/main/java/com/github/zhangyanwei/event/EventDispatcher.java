package com.github.zhangyanwei.event;

public interface EventDispatcher {

    void register(EventType type, EventHandler handler, int priority);

    void unregister(EventType type, EventHandler handler);

    void dispatch(Event event);

}
