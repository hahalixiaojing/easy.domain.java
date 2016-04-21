package easy.domain.repository.framework;

public interface IRepositoryFactory {
	<T> T get(Class<T> cls);
}
