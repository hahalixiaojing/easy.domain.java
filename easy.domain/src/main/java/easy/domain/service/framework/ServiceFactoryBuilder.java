package easy.domain.service.framework;

import java.io.InputStream;

public class ServiceFactoryBuilder {
	public IServiceFactory build(InputStream stream){
		return new ServiceFactory(stream);
	}
}
