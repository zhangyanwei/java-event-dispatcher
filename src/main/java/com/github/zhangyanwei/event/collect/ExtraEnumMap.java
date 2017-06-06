package com.github.zhangyanwei.event.collect;

import java.util.EnumMap;

public class ExtraEnumMap<K extends Enum<K>, V> extends ExtraMap<K, V> {

    public ExtraEnumMap(EnumMap<K, V> map) {
        super(map);
    }

}
