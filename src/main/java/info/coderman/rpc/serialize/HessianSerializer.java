package info.coderman.rpc.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import info.coderman.rpc.core.serialize.Serializer;

/**
 * 
 * @author yuezixin 2016-8-24 13:46:38
 *
 */
public class HessianSerializer implements Serializer {
	private static final Logger LOG = LoggerFactory.getLogger(JsonSerializer.class);

	private static final Objenesis objenesis = new ObjenesisStd(true);

	@Override
	public <T> byte[] serialize(T obj) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return os.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clz) {
		T t = (T) objenesis.newInstance(clz);

		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		HessianInput hi = new HessianInput(is);
		try {
			t = (T) hi.readObject();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return t;
	}
}