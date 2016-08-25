package info.coderman.rpc.core.serialize;
/**
 * 序列化
 * @author yuezixin 2016-8-25 12:12:49
 *
 */
public interface Serializer {
	<T> byte[] serialize(T obj);
	
	<T> T deserialize(byte[] bytes, Class<T> clz);
	
	public enum Type{
		JSON,HESSIAN,PROTOSTUFF
	}
}
