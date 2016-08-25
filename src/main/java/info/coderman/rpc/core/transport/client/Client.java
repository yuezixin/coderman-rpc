package info.coderman.rpc.core.transport.client;

import java.io.Closeable;

import info.coderman.rpc.core.protocol.RpcRequest;
import info.coderman.rpc.core.protocol.RpcResponse;
/**
 * 客户端
 * @author yuezixin 2016-8-24 14:50:05
 *
 */
public interface Client extends Closeable{
	public RpcResponse send(RpcRequest request) throws Exception;
	
	public void init(int port,String host,String serialize);
	
	public void create();
	
	public void connect();
}
