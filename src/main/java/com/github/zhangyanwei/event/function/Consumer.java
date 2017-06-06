package com.github.zhangyanwei.event.function;

public interface Consumer<T> {

    void accept(T t);

}