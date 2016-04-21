package easy.domain.service.framework;

public interface IServiceFactory {
	<T> T get(Class<T> cls);
}
