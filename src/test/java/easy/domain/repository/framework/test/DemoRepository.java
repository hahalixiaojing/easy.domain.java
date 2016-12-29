package easy.domain.repository.framework.test;

import java.util.List;

import easy.domain.repository.framework.IDao;
import easy.domain.test.User;

public class DemoRepository implements IDemoRepository, IDao {

	@Override
	public void add() {
		

	}

	@Override
	public List<User> findBy(Integer[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findBy(Integer key) {
		return new User("lixiaojing",1);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(User item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(User item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		
	}

}
