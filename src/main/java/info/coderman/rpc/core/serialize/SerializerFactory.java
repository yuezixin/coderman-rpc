package info.coderman.rpc.core.serialize;

import java.util.HashMap;
import java.util.Map;

import info.coderman.rpc.serialize.HessianSerializer;
import info.coderman.rpc.serialize.JsonSerializer;
import info.coderman.rpc.serialize.ProtostuffSerializer;

/**
 * 
 * @author yuezixin 2016-8-24 14:11:29
 *
 */
public class SerializerFactory {
	private static Serializer serializer=null;
	
	private SerializerFactory(){}
	
	@SuppressWarnings("serial")
	private static final Map<String,Serializer> serializerMap=new HashMap<String,Serializer>(){
		{
			put(Serializer.Type.JSON.name(),new JsonSerializer());
			put(Serializer.Type.HESSIAN.name(),new HessianSerializer());
			put(Serializer.Type.PROTOSTUFF.name(),new ProtostuffSerializer());

		}
	}; 
	
	public static Serializer getInstance(String serializerTye){
		if(serializer==null){
			serializer=serializerMap.get(serializerTye.toUpperCase());
		}
		return serializer;
	}
}
