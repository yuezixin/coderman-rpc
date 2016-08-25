package info.coderman.rpc.service;

import info.coderman.rpc.bean.User;

public interface UserService {
	public String getName(int id);
	public User findUser(int id);
}
