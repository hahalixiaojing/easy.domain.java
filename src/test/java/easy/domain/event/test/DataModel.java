package easy.domain.event.test;

import java.util.Date;

import easy.domain.event.IDomainEvent;

public class DataModel implements IDomainEvent {
	private String url;

	public DataModel(Date oDate, String url) {
		this.url = url;
	}

	public String Url() {
		return this.url;
	}

	@Override
	public String getBusinessId() {
		return null;
	}
}
