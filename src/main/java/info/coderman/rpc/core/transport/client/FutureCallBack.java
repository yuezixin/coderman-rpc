package info.coderman.rpc.core.transport.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import info.coderman.rpc.core.protocol.RpcResponse;

/**
 * 获取客户端请求返回结果
 * @author yuezixin 2016-8-24 15:43:15
 *
 */
public class FutureCallBack {
	private static final Map<String,RpcResponse> future=new ConcurrentHashMap<>();

	public static void setResponse(String messageId,RpcResponse response){
		future.put(messageId, response);
	}
	public static RpcResponse getAndRemove(String messageId){
		RpcResponse response=future.get(messageId);
		future.remove(messageId);
		return response;
	}
}
