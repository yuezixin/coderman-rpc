package info.coderman.rpc.core.transport.server;

import java.util.HashMap;
import java.util.Map;

import info.coderman.rpc.core.serialize.Serializer;
import info.coderman.rpc.core.serialize.SerializerFactory;

/**
 * 
 * @author yuezixin 2016-8-22 14:50:21
 *
 */
public abstract class AbstractServer implements Server{
	
	//config
    protected int port;
    protected String host;
    protected Map<String, Object> serviceMap = new HashMap<>();
    protected Serializer serializer;
    //参数初始化
    public void init(int port,String host,String serialize,Map<String,Object> serviceMap){
    	this.port=port;
    	this.host=host;
    	this.serviceMap=serviceMap;
    	this.serializer=SerializerFactory.getInstance(serialize);
    }
}
