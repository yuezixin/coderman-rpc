package info.coderman.rpc.transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import info.coderman.rpc.core.constant.Transport;
import info.coderman.rpc.core.protocol.RpcRequest;
import info.coderman.rpc.core.protocol.RpcResponse;
import info.coderman.rpc.core.serialize.Serializer;
import info.coderman.rpc.core.transport.client.Client;
import info.coderman.rpc.core.transport.client.ClientFactory;
import info.coderman.rpc.registry.ServiceDiscovery;

/**
 * 
 * @author yuezixin 2016-8-24 14:51:45
 *
 */
public class RpcClientProxy{

    private String serviceAddress;
    private ServiceDiscovery serviceDiscovery;
    //网络通信方式
    private String transportType=Transport.NETTY.name();
    private String serialize=Serializer.Type.HESSIAN.name();


    public RpcClientProxy() {
	}
    
	public RpcClientProxy(ServiceDiscovery serviceDiscovery, String transportType, String serialize) {
 		this.serviceDiscovery = serviceDiscovery;
		this.transportType = transportType;
		this.serialize = serialize;
	}

	public ServiceDiscovery getServiceDiscovery() {
		return serviceDiscovery;
	}

	public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getSerialize() {
		return serialize;
	}

	public void setSerialize(String serialize) {
		this.serialize = serialize;
	}

	@SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass) {
        // 创建动态代理对象
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                         RpcRequest request = buildRequest(method, args);
                         if (serviceDiscovery != null) {
                            String serviceName = interfaceClass.getName();
                            //根据serviceName查找可用的sever
                            serviceAddress = serviceDiscovery.discover(serviceName);
                         }
                        if (StringUtils.isEmpty(serviceAddress)) {
                            throw new RuntimeException("server address is empty");
                        }
                        //解析server ip 、 port
                        String[] array = StringUtils.split(serviceAddress, ":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);
                        //发送请求
                        Client client = ClientFactory.getInstance(transportType);
                        client.init(port, host, serialize);
                        RpcResponse response = client.send(request);
                        if (response == null) {
                            throw new RuntimeException("response is null");
                        }
                        //请求响应
                        if (response.hasException()) {
                            throw response.getException();
                        } else {
                            return response.getResult();
                        }
                    }

                }
        );
    }
	/**
	 * 构建请求信息
	 * @param method
	 * @param args
	 * @return
	 */
	private RpcRequest buildRequest(Method method, Object[] args) {
		RpcRequest request = new RpcRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
		return request;
	}
}
