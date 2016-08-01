package easy.domain.event.test;

import java.time.LocalDate;

import easy.domain.event.IDomainEvent;

public class DataModel implements IDomainEvent {
	private String url;

	public DataModel(LocalDate oDate, String url) {
		this.url = url;
	}

	public String Url() {
		return this.url;
	}
}
