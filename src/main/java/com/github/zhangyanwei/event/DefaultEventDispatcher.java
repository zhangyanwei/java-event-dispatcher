package com.github.zhangyanwei.event;

import com.github.zhangyanwei.event.collect.ExtraEnumMap;
import com.github.zhangyanwei.event.collect.ExtraList;
import com.github.zhangyanwei.event.function.Consumer;
import com.github.zhangyanwei.event.function.Predicate;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

import java.util.LinkedList;

public class DefaultEventDispatcher implements EventDispatcher {

    private ExtraEnumMap<EventType, HandlerHolder> handlerHolders =
            new ExtraEnumMap<EventType, HandlerHolder>(Maps.<EventType, HandlerHolder>newEnumMap(EventType.class));

    @Override
    public void register(EventType type, EventHandler handler, int priority) {
        HandlerHolder handlerHolder = this.handlerHolders.getOrDefault(type, new HandlerHolder());
        handlerHolder.add(handler, priority);
        this.handlerHolders.putIfAbsent(type, handlerHolder);
    }

    @Override
    public void unregister(EventType type, final EventHandler handler) {
        this.handlerHolders.ifPresent(type, new Consumer<HandlerHolder>() {
            @Override
            public void accept(HandlerHolder handlerHolder) {
                handlerHolder.remove(handler);
            }
        });
    }

    @Override
    public void dispatch(final Event event) {
        this.handlerHolders.ifPresent(event.getType(), new Consumer<HandlerHolder>() {
            @Override
            public void accept(HandlerHolder handlerHolder) {
                handlerHolder.dispatch(event);
            }
        });
    }

    private final static class HandlerHolder {

        private ExtraList<PriorityEventHandler> handlers = new ExtraList<PriorityEventHandler>(new LinkedList<PriorityEventHandler>());

        void add(EventHandler handler, int priority) {
            this.handlers.add(new PriorityEventHandler(priority, handler));
        }

        void remove(final EventHandler handler) {
            this.handlers.removeIf(new Predicate<PriorityEventHandler>() {
                @Override
                public boolean test(PriorityEventHandler priorityEventHandler) {
                    return handler.equals(priorityEventHandler.getHandler());
                }
            });
        }

        void dispatch(Event event) {
            ImmutableList<PriorityEventHandler> orderedHandlers = orderHandlers();
            for (PriorityEventHandler orderedHandler : orderedHandlers) {
                //noinspection unchecked
                orderedHandler.getHandler().handle(event);
            }
        }

        private ImmutableList<PriorityEventHandler> orderHandlers() {
            //noinspection unchecked
            return Ordering.natural().onResultOf(new Function<PriorityEventHandler, Integer>() {
                        @Override
                        public Integer apply(PriorityEventHandler handler) {
                            return handler.getPriority();
                        }
                    }).immutableSortedCopy(this.handlers);
        }

    }

    private final static class PriorityEventHandler {

        private int priority;

        PriorityEventHandler(int priority, EventHandler handler) {
            this.priority = priority;
            this.handler = handler;
        }

        private EventHandler handler;

        int getPriority() {
            return priority;
        }

        EventHandler getHandler() {
            return handler;
        }
    }
}
