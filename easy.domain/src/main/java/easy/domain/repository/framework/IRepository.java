package easy.domain.repository.framework;

import java.util.List;

public interface IRepository<T,TKey>
{
	List<T> findBy(TKey[] keys);
    T findBy(TKey key);
    List<T> findAll();
    void add(T item);
    void update(T item);
    void remove(T item);
    void removeAll();
}
