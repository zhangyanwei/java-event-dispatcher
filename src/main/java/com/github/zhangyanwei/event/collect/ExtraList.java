package com.github.zhangyanwei.event.collect;

import com.github.zhangyanwei.event.function.Predicate;
import org.apache.commons.collections.list.AbstractListDecorator;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class ExtraList<E> extends AbstractListDecorator {

    public ExtraList(List<E> list) {
        super(list);
    }

    public boolean removeIf(Predicate<? super E> filter) {
        if (filter == null) {
            throw new NullPointerException();
        }
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

}
