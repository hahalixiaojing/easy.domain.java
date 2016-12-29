package easy.domain.repository.framework;

import java.io.InputStream;

public class RepositoryFactoryBuilder {
	public IRepositoryFactory build(InputStream stream){
		return new RepositoryFactory(stream);
	}
}
