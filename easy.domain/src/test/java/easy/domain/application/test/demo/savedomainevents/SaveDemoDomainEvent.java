package easy.domain.application.test.demo.savedomainevents;

import java.time.LocalDate;

import easy.domain.event.IDomainEvent;

public class SaveDemoDomainEvent implements IDomainEvent {

	private String name;
	private LocalDate occuredOn;
	
	public SaveDemoDomainEvent(String name) {
		this.name=name;
		this.occuredOn= LocalDate.now();
	}
	public SaveDemoDomainEvent() {
	}
	
	@Override
	public LocalDate occurredOn() {
		return this.occuredOn;
	}

	public String getName() {
		return name;
	}

}
