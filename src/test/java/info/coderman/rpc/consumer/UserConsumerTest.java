package info.coderman.rpc.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.coderman.rpc.service.UserService;
import info.coderman.rpc.transport.RpcClientProxy;

@ContextConfiguration("classpath:spring-client.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserConsumerTest {
	@Autowired
    private RpcClientProxy rpcClientProxy;
	
	@Test
	public void getNameTest(){
		UserService userService=rpcClientProxy.create(UserService.class);
		System.out.println(userService.getName(1));
	}
	@Test
	public void findUserTest(){
		UserService userService=rpcClientProxy.create(UserService.class);
		System.out.println(userService.findUser(1).toString());
	}
}
