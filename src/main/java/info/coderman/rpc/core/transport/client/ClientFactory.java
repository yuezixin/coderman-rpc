package info.coderman.rpc.core.transport.client;

import java.util.HashMap;
import java.util.Map;

import info.coderman.rpc.core.constant.Transport;
import info.coderman.rpc.transport.netty.client.NettyClient;

/**
 * 
 * @author yuezixin 2016-8-23 13:57:13
 *
 */
public class ClientFactory {
 
	private static Client client=null;
	private ClientFactory(){}
	
 	@SuppressWarnings("serial")
	private static final Map<String,Client> clientMap=new HashMap<String,Client>(){
		{
			put(Transport.NETTY.name(),new NettyClient());
		}
	};
	
	public static Client getInstance(String transportType){
		if(client==null){
			client=clientMap.get(transportType);
		}
		return client;
	}
}
