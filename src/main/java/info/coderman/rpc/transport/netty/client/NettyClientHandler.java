package info.coderman.rpc.transport.netty.client;

import info.coderman.rpc.core.protocol.RpcResponse;
import info.coderman.rpc.core.transport.client.FutureCallBack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 
 * @author yuezixin 2016-8-25 13:34:04
 *
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
 		FutureCallBack.setResponse(msg.getMessageId(), msg);
	}

}
