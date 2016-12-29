package easy.domain.application.test.demo.saveevents;

import easy.domain.event.IDomainEventSubscriber;
import easy.domain.event.ISubscriber;

public class SaveDemoSubscriber implements IDomainEventSubscriber<SaveDemoDomainEvent>,
		ISubscriber {

	@Override
	public void handleEvent(SaveDemoDomainEvent aDomainEvent) {
		System.out.print(Thread.currentThread().getId());
		System.out.println(aDomainEvent.getName());
	}

	@Override
	public Class<SaveDemoDomainEvent> suscribedToEventType() {
		return SaveDemoDomainEvent.class;
	}

}
