package easy.domain.event.test;

import java.time.LocalDate;

import easy.domain.event.IDomainEvent;

public class DataModel implements IDomainEvent {
	private LocalDate occurredOn;
	private String url;

	public DataModel(LocalDate oDate, String url) {
		this.occurredOn = oDate;
		this.url = url;
	}

	@Override
	public LocalDate occurredOn() {
		return this.occurredOn;
	}

	public String Url() {
		return this.url;
	}
}
