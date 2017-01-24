package easy.domain.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 事件发布器
 */
public class DomainEventPublisher {
    private static final ExecutorService pool;

    static {

        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * 发布事件
     *
     * @param aDomainEvent
     */
    @SuppressWarnings("unchecked")
    public <T extends IDomainEvent> void publish(final T aDomainEvent, List<ISubscriber> subscribers) {
        for (ISubscriber sub : subscribers) {
            IDomainEventSubscriber<T> subscribedTo = (IDomainEventSubscriber<T>) sub;

            if (subscribedTo != null && subscribedTo.subscribedToEventType() == aDomainEvent.getClass()) {
                Task<T> task = new Task<T>(subscribedTo, aDomainEvent);
                pool.execute(task);
            }
        }
    }
}
