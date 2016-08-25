package info.coderman.rpc.core.transport.server;

import java.util.HashMap;
import java.util.Map;

import info.coderman.rpc.core.constant.Transport;
import info.coderman.rpc.transport.netty.server.NettyServer;

/**
 *  
 * @author yuezixin 2016-8-23 13:57:13
 *
 */
public class ServerFactory {
 
	private static Server server=null;
	private ServerFactory(){}
	
 	@SuppressWarnings("serial")
	private static final Map<String,Server> serverMap=new HashMap<String,Server>(){
		{
			put(Transport.NETTY.name(),new NettyServer());
		}
	};
	
	public static Server getInstance(String transportType){
		if(server==null){
			server=serverMap.get(transportType);
		}
		return server;
	}
}
