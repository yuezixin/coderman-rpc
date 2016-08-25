package info.coderman.rpc.service.impl;

import info.coderman.rpc.annotation.Service;
import info.coderman.rpc.bean.User;
import info.coderman.rpc.service.UserService;
@Service(UserService.class)
public class UserServiceImpl implements UserService {

	@Override
	public String getName(int id) {
 		return "关键时候只能呵呵了-->"+id;
	}

	@Override
	public User findUser(int id) {
		User u=new User();
		u.setId(id);
		u.setAge(123);
		u.setName("老司机");
		return u;
	}

}
