package info.coderman.rpc.registry.zookeeper;

import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.coderman.rpc.core.constant.Global;
import info.coderman.rpc.registry.ServiceRegistry;
/**
 * 服务注册
 * @author yuezixin 2016-8-25 14:03:30
 *
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry{
    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);
    
    private final ZkClient zkClient;
    
    
    
	public ZooKeeperServiceRegistry(String  zkAddress) {
		String registryNode=Global.ZK_REGISTRY_PATH;
 		zkClient=new ZkClient(zkAddress, Global.ZK_SESSION_TIMEOUT, Global.ZK_CONNECTION_TIMEOUT);
 		LOG.info("connect zookeeper server {}",zkAddress);
 		//create registry node
 		if(!zkClient.exists(registryNode)){
 			zkClient.createPersistent(registryNode);
			LOG.info("create registry node-->{}",registryNode);
 		}
	}



	@Override
	public void register(Map<String,Object> serviceMap, String serviceAddress) {
		for(String interfaceName:serviceMap.keySet()){
	 		//create service node
			String serviceNode=Global.ZK_REGISTRY_PATH.concat("/").concat(interfaceName);
			if(!zkClient.exists(serviceNode)){
				zkClient.createPersistent(serviceNode);
				LOG.info("create service node-->{}",serviceNode);
			}
			
			String addressNode=serviceNode.concat("/").concat("address-");
			addressNode=zkClient.createEphemeralSequential(addressNode, serviceAddress);
			LOG.info("create address node-->{}",addressNode);
		}

	}
}
