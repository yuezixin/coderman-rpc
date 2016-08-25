package info.coderman.rpc.registry;

import java.util.Map;

public interface ServiceRegistry {

	public void register(Map<String,Object> serviceMap, String serviceAddress);
}