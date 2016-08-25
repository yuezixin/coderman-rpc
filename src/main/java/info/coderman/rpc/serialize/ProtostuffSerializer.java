package info.coderman.rpc.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import info.coderman.rpc.core.serialize.Serializer;

/**
 * 
 * @author yuezixin 2016-8-24 13:43:49
 *
 */
public class ProtostuffSerializer implements Serializer {
	private static final Logger LOG = LoggerFactory.getLogger(ProtostuffSerializer.class);

	private static final Objenesis objenesis = new ObjenesisStd(true);
	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

	private static <T> Schema<T> getSchema(Class<T> clz) {
		@SuppressWarnings("unchecked")
		Schema<T> schema = (Schema<T>) cachedSchema.get(clz);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(clz);
			if (schema != null) {
				cachedSchema.put(clz, schema);
			}
		}
		return schema;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> byte[] serialize(T obj) {
		Class<T> cls = (Class<T>) obj.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		byte[] bytes = null;
		try {
			Schema<T> schema = getSchema(cls);
			bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
		return bytes;
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clz) {
		T t = (T) objenesis.newInstance(clz);
		try {
			Schema<T> schema = getSchema(clz);
			ProtostuffIOUtil.mergeFrom(bytes, t, schema);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return t;
	}
}
