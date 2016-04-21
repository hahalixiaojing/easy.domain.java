package easy.domain.application;

import java.util.HashMap;
import java.util.List;

import easy.domain.event.ISubscriber;

public interface IDomainEventSubscriberLoader {
	HashMap<String, List<ISubscriber>> find(IApplication application);
}
