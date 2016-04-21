package easy.domain.event;

/**
 * 事件订阅接口
 * @author lixiaojing
 *
 * @param <T>
 */
public interface IDomainEventSubscriber<T extends IDomainEvent> extends ISubscriber {
    /**
     * 事件处理
     */
	void handleEvent(T aDomainEvent);
    /**
     * 事件类型
     * @return
     */
    Class<T> suscribedToEventType();
}
