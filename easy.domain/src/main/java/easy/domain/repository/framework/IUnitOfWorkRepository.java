package easy.domain.repository.framework;

import easy.domain.base.IAggregateRoot;

public interface IUnitOfWorkRepository {
	void persistNewItem(IAggregateRoot item);
    void persistUpdatedItem(IAggregateRoot item);
    void persistDeletedItem(IAggregateRoot item);
}
