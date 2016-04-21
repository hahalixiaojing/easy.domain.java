package easy.domain.repository.framework;

import java.util.List;

import easy.domain.base.IAggregateRoot;

public abstract class RepositoryBase<T extends IAggregateRoot, TKey> implements
		IRepository<T, TKey>, IUnitOfWorkRepository {

	@SuppressWarnings("unchecked")
	@Override
	public void persistNewItem(IAggregateRoot item) {
		this.persistNew((T) item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void persistUpdatedItem(IAggregateRoot item) {
		this.persistUpdated((T) item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void persistDeletedItem(IAggregateRoot item) {
		this.persistDeleted((T) item);
	}

	@Override
	public abstract List<T> findBy(TKey[] keys);

	@Override
	public abstract T findBy(TKey key);

	@Override
	public abstract List<T> findAll();

	@Override
	public abstract void removeAll();

	@Override
	public void add(T item) {
		this.persistNewItem(item);
	}

	@Override
	public void update(T item) {
		this.persistUpdatedItem(item);
	}

	@Override
	public void remove(T item) {
		this.persistUpdatedItem(item);
	}

	protected abstract void persistNew(T item);

	protected abstract void persistUpdated(T item);

	protected abstract void persistDeleted(T item);

}
