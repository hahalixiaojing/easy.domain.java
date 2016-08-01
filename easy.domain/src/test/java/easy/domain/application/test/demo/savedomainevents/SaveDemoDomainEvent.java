package easy.domain.application.test.demo.savedomainevents;


import easy.domain.event.IDomainEvent;

public class SaveDemoDomainEvent implements IDomainEvent {

	private String name;
	
	public SaveDemoDomainEvent(String name) {
		this.name=name;
	}
	public SaveDemoDomainEvent() {
	}
	
	public String getName() {
		return name;
	}

}
