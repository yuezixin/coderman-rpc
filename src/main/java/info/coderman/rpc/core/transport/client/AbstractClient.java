package info.coderman.rpc.core.transport.client;

import java.io.IOException;

import info.coderman.rpc.core.serialize.Serializer;
import info.coderman.rpc.core.serialize.SerializerFactory;

/**
 * 
 * @author yuezixin 2016-8-22 14:50:21
 *
 */
public abstract class AbstractClient implements Client{
	
	//config
    protected int port;
    protected String host;
    protected Serializer serializer;
    //参数初始化
    public void init(int port,String host,String serialize){
    	this.port=port;
    	this.host=host;
     	this.serializer=SerializerFactory.getInstance(serialize);
    }
    @Override
	public void close() throws IOException {
 
	}

	@Override
	public void create() {
 		
	}

	@Override
	public void connect() {
 		
	}
}
