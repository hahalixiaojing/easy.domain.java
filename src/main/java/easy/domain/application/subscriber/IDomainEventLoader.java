package easy.domain.application.subscriber;

import easy.domain.application.IApplication;

import java.util.List;

public interface IDomainEventLoader {
	List<Class<?>> load(IApplication application) throws ClassNotFoundException;
}
