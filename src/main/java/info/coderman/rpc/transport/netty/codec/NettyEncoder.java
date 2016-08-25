package info.coderman.rpc.transport.netty.codec;

import info.coderman.rpc.core.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author yuezixin 2016-8-23 14:21:09
 *
 */
@SuppressWarnings("rawtypes")
public class NettyEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;
    private Serializer serializer;
    public NettyEncoder(Class<?> genericClass,Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer=serializer;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] bytes = serializer.serialize(in);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}