package com.github.zhangyanwei.event.collect;

import com.github.zhangyanwei.event.function.Consumer;
import org.apache.commons.collections.map.AbstractMapDecorator;

import java.util.Map;

@SuppressWarnings("unchecked")
public class ExtraMap<K, V> extends AbstractMapDecorator {

    public ExtraMap(Map<K, V> map) {
        super(map);
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = (V) get(key);
        if (value == null) {
            return  defaultValue;
        }

        return value;
    }

    public void putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            put(key, value);
        }
    }

    public void ifPresent(K key, Consumer<V> consumer) {
        if (!containsKey(key)) {
            consumer.accept((V) get(key));
        }
    }

}
