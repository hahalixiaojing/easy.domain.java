package easy.domain.application.subscriber;

import java.util.HashMap;
import java.util.List;

import easy.domain.application.IApplication;
import easy.domain.event.ISubscriber;

public interface IDomainEventSubscriberLoader {
	HashMap<String, List<ISubscriber>> find(IApplication application);
}
