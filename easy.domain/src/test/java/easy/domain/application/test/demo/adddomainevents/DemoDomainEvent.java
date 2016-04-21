package easy.domain.application.test.demo.adddomainevents;

import java.time.LocalDate;

import easy.domain.event.IDomainEvent;

public class DemoDomainEvent implements IDomainEvent {

	private String name;
	private LocalDate occurredOn;
	
	public DemoDomainEvent() {
	}
	
	public DemoDomainEvent(String name) {
		this.name = name;
		this.occurredOn = LocalDate.now();
	}
	
	@Override
	public LocalDate occurredOn() {
		return this.occurredOn;
	}

	public String getName() {
		return name;
	}
}
