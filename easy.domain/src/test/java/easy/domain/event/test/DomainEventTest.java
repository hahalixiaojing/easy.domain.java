package easy.domain.event.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import easy.domain.event.DomainEventPublisher;
import easy.domain.event.IDomainEventSubscriber;

public class DomainEventTest {
	@Test
	public void domainEventPublisherTest() {

		DomainEventPublisher publisher = new DomainEventPublisher();

		publisher.subscribe(new IDomainEventSubscriber<DataModel>() {
			public void handleEvent(DataModel domainEvent) {
				System.out.println("=========" + "订阅事件发布");
			};

			@Override
			public Class<DataModel> suscribedToEventType() {
				return DataModel.class;
			}
		});
		publisher.subscribe(new IDomainEventSubscriber<DataModel>() {
			public void handleEvent(DataModel domainEvent) {
				System.out.println("=========" + domainEvent);

				URL uri;
				try {
					uri = new URL("http://www.baidu.com");
					URLConnection conn = uri.openConnection();
					InputStream stream = conn.getInputStream();

					BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			}

			@Override
			public Class<DataModel> suscribedToEventType() {
				return DataModel.class;
			};
		});

		publisher.publish(new DataModel(java.time.LocalDate.now(), "http://www.baidu.com"));

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
