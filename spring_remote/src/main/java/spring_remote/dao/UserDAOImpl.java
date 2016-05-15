package spring_remote.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements IUserDAO {

	@Override
	public String sayHello(String name) {
		return "hello: " + name;
	}

}
