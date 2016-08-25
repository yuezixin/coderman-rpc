package info.coderman.rpc.transport.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.coderman.rpc.core.protocol.RpcRequest;
import info.coderman.rpc.core.protocol.RpcResponse;
import info.coderman.rpc.core.transport.server.AbstractServer;
import info.coderman.rpc.transport.netty.codec.NettyDecoder;
import info.coderman.rpc.transport.netty.codec.NettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * 
 * @author yuezixin 2016-8-24 14:13:40
 *
 */
public class NettyServer extends AbstractServer{
    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);
  

	@Override
	public void start() {
		   EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            // 创建并初始化 Netty 服务端 Bootstrap 对象
	            ServerBootstrap bootstrap = new ServerBootstrap();
	            bootstrap.group(bossGroup, workerGroup);
	            bootstrap.channel(NioServerSocketChannel.class);
	            bootstrap.childHandler(new NettyServerInitializer());
	            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
	            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
 	            // 启动 RPC 服务器
	            ChannelFuture future = bootstrap.bind(host,port).sync();
  	            LOG.info("server started on port {}", port);
	            // 关闭 RPC 服务器
	            future.channel().closeFuture().sync();
	        } catch (InterruptedException e) {
 				e.printStackTrace();
			} finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	}
	
	class NettyServerInitializer extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			 ChannelPipeline pipeline = ch.pipeline();
             pipeline.addLast(new NettyDecoder(RpcRequest.class,serializer)); 
             pipeline.addLast(new NettyEncoder(RpcResponse.class,serializer));
             pipeline.addLast(new NettyServerHandler(serviceMap));			
		}
	}
}
