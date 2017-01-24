package easy.domain.application;

import easy.domain.event.IDomainEvent;
import easy.domain.event.IDomainEventSubscriber;


public class UpdateEsSubscriber implements IDomainEventSubscriber<TestDomainEvent> {


    @Override
    public void handleEvent(TestDomainEvent aDomainEvent) {
        System.out.println(aDomainEvent.orderId);
        System.out.println("event thread id=" + Thread.currentThread().getId());

    }


    @Override
    public Class<?> subscribedToEventType() {
        return TestDomainEvent.class;
    }
}
