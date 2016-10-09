package easy.domain.application.test.demo.addevents;

import easy.domain.event.IDomainEventSubscriber;
import easy.domain.event.ISubscriber;

public class DemoSubscriber implements IDomainEventSubscriber<DemoDomainEvent>, ISubscriber {

	@Override
	public void handleEvent(DemoDomainEvent aDomainEvent) {
		
		System.out.print(Thread.currentThread().getId());
		System.out.println(aDomainEvent.getName());
		
	}

	@Override
	public Class<DemoDomainEvent> suscribedToEventType() {

		return DemoDomainEvent.class;
	}

}
