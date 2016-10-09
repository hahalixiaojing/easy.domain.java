package easy.domain.application.test.demo.addevents;


import easy.domain.event.IDomainEvent;

public class DemoDomainEvent implements IDomainEvent {

	private String name;
	
	public DemoDomainEvent() {
	}
	
	public DemoDomainEvent(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}
}
