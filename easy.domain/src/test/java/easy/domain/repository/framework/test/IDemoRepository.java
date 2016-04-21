package easy.domain.repository.framework.test;

import easy.domain.repository.framework.IRepository;
import easy.domain.test.User;

public interface IDemoRepository extends IRepository<User,Integer> {
	void add();
	
}
