package info.coderman.rpc.transport;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import info.coderman.rpc.annotation.Service;
import info.coderman.rpc.core.constant.Transport;
import info.coderman.rpc.core.serialize.Serializer;
import info.coderman.rpc.core.transport.server.Server;
import info.coderman.rpc.core.transport.server.ServerFactory;
import info.coderman.rpc.core.util.IpUtils;
import info.coderman.rpc.registry.ServiceRegistry;

/**
 * 
 * @author yuezixin 2016-8-23 13:11:34
 *
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(RpcServer.class);
    //服务注册中心
    private ServiceRegistry serviceRegistry;
    //网络通信方式
    private String transportType=Transport.NETTY.name();
    //server port
    private int port;
    //序列化方式
    private String serialize=Serializer.Type.HESSIAN.name();
    /**
     * 存放 服务名 与 服务对象 之间的映射关系
     */
    private Map<String, Object> serviceMap = new HashMap<>();

    
	public RpcServer() {
 	}

	public RpcServer(ServiceRegistry serviceRegistry, int port) {
		this.serviceRegistry = serviceRegistry;
		this.port = port;
	}
	
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSerialize() {
		return serialize;
	}

	public void setSerialize(String serialize) {
		this.serialize = serialize;
	}
	
	/**
	 * 加载service
	 */
	@Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(Service.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                Service rpcService = serviceBean.getClass().getAnnotation(Service.class);
                String serviceName = rpcService.value().getName();
                serviceMap.put(serviceName, serviceBean);
            }
        }
        LOG.info("load service class to serviceMap");
     }

	@Override
	public void afterPropertiesSet() throws Exception {
		// 获取 RPC 服务器的 IP 地址与端口号
		String host = IpUtils.getBindIp();
		// 注册 RPC 服务地址
        LOG.info("register service start! ");
		serviceRegistry.register(serviceMap, host + ":" + port);
        LOG.info("register service finished! ");

		// 启动 rpc server
		Server server=ServerFactory.getInstance(transportType);
		server.init(port, host,serialize, serviceMap);
		server.start();
 	}
 }
