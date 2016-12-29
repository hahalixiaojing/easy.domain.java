package easy.domain.service.framework.test;

import easy.domain.service.framework.IService;

public class DemoService implements IDemoService,IService {

	@Override
	public int GetByUserId(int userId) {
		
		return userId;

	}

}
