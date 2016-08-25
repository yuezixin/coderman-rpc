package info.coderman.rpc.core.transport.server;

import java.util.Map;
/**
 * 
 * @author yuezixin 2016-8-25 12:14:15
 *
 */
public interface Server {
	/**
	 * 参数初始化
	 * @param port
	 * @param host
	 * @param serialize
	 * @param serviceMap
	 */
	public void init(int port,String host,String serialize,Map<String,Object> serviceMap);
	/**
	 * 开启服务
	 */
	public void start();
	
}
