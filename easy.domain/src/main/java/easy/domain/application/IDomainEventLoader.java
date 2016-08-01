package easy.domain.application;

import java.util.List;

public interface IDomainEventLoader {
	List<Class<?>> load(IApplication application);
}
