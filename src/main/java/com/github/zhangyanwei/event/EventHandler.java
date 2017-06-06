package com.github.zhangyanwei.event;

public interface EventHandler<E extends Event> {
    void handle(E event);
}
