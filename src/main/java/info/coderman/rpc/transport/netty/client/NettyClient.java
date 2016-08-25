package info.coderman.rpc.transport.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.coderman.rpc.core.protocol.RpcRequest;
import info.coderman.rpc.core.protocol.RpcResponse;
import info.coderman.rpc.core.transport.client.AbstractClient;
import info.coderman.rpc.core.transport.client.FutureCallBack;
import info.coderman.rpc.transport.netty.codec.NettyDecoder;
import info.coderman.rpc.transport.netty.codec.NettyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
 * 
 * @author yuezixin 2016-8-24 17:00:04
 *
 */
public class NettyClient extends AbstractClient {

	private static final Logger LOG = LoggerFactory.getLogger(NettyClient.class);

	@Override
	public RpcResponse send(RpcRequest request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new NettyClientInitializer());
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			ChannelFuture future = bootstrap.connect(host, port).sync();
			LOG.info("connected server-->{}",host);
			Channel channel = future.channel();
			channel.writeAndFlush(request).sync();
			channel.closeFuture().sync();
			return FutureCallBack.getAndRemove(request.getMessageId());
		} finally {
			group.shutdownGracefully();
		}
	}
	class NettyClientInitializer extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast(new NettyEncoder(RpcRequest.class, serializer)); 
			pipeline.addLast(new NettyDecoder(RpcResponse.class, serializer));
			pipeline.addLast(new NettyClientHandler()); 			
		}
 	}
}
