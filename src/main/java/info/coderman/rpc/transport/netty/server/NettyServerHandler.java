package info.coderman.rpc.transport.netty.server;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.coderman.rpc.core.constant.Global;
import info.coderman.rpc.core.protocol.RpcRequest;
import info.coderman.rpc.core.protocol.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * 
 * @author yuezixin 2016-8-23 14:48:12
 *
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(NettyServerHandler.class);

    private final Map<String, Object> serviceMap;
    private ExecutorService executorService;
    public NettyServerHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
        this.executorService=Executors.newFixedThreadPool(Global.PROCESSORS+1);
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
    	executorService.submit(new Runnable() {			
			@Override
			public void run() {
 		        RpcResponse response = new RpcResponse();
		        response.setMessageId(request.getMessageId());
		        try {
		            Object result = handle(request);
		            response.setResult(result);
		        } catch (Exception e) {
		            LOG.error("Server handle request failure", e);
		            response.setException(e);
		        }
		        //返回对象并自动关闭连接
		        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
 					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						LOG.info("send response,the messageId is: {}",request.getMessageId());
					}
				}).addListener(ChannelFutureListener.CLOSE);				
			}
		});
    }

    private Object handle(RpcRequest request) throws Exception {
        // 获取服务对象
        String serviceName = request.getInterfaceName();
        Object serviceBean = serviceMap.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }
        // 获取反射调用所需的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("netty server caught exception", cause);
        ctx.close();
    }
}
