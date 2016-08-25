package info.coderman.rpc.serialize;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.coderman.rpc.core.serialize.Serializer;

/**
 * 
 * @author yuezixin 2016-8-23 14:39:31
 *
 */
public class JsonSerializer implements Serializer{
    private static final Logger LOG = LoggerFactory.getLogger(JsonSerializer.class);

    private static final ObjectMapper mapper = new ObjectMapper();
	@Override
	public <T> byte[] serialize(T obj) {
    	byte[] bytes = null;

  		try {
  			bytes = mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage(),e);
		}
  		return bytes;
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clz) {
		try {
			return mapper.readValue(bytes, clz);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
 		}
		return null; 
		 
 	}
}