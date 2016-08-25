package info.coderman.rpc.registry.zookeeper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.coderman.rpc.core.constant.Global;
import info.coderman.rpc.registry.ServiceDiscovery;
/**
 * 服务发现
 * @author yuezixin 2016-8-25 11:49:52
 *
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {

    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);
    private final ZkClient zkClient;
    private static Map<String,Set<String>> serviceMap=new ConcurrentHashMap<>();

    private String zkAddress;

    public ZooKeeperServiceDiscovery(String zkAddress) {
        this.zkAddress = zkAddress;
        // 创建 ZooKeeper 客户端
        LOG.debug("connect zookeeper");
        zkClient = new ZkClient(zkAddress, Global.ZK_SESSION_TIMEOUT, Global.ZK_CONNECTION_TIMEOUT);
 		LOG.info("discovery connected zookeeper server {}",zkAddress);
 		queryNodes();
    }
    /**
     * 查询所有服务 
     */
    private void queryNodes() {
    	Map<String,Set<String>> nodesMap=new ConcurrentHashMap<>();
 		List<String> intfServices=zkClient.getChildren(Global.ZK_REGISTRY_PATH);
 		for (String intfService : intfServices) {
 			List<String> addresses=zkClient.getChildren(Global.ZK_REGISTRY_PATH.concat("/").concat(intfService));
 			Set<String> addressSet=new HashSet<>();
 			for (String address : addresses) {
 				String addressNode=Global.ZK_REGISTRY_PATH.concat("/").concat(intfService).concat("/").concat(address);
 				addressSet.add(zkClient.readData(addressNode).toString());
			}
 			nodesMap.put(intfService, addressSet);
 		}
 		serviceMap=nodesMap;
     }

	@Override
	public String discover(String name) {
		String address;
		List<String> addresses = new ArrayList<>(serviceMap.get(name));
		int size = addresses.size();
		if (size == 1) {
			address = addresses.get(0);
		} else {
			address = addresses.get(ThreadLocalRandom.current().nextInt(size));
		}
		return address;
	}
}