package info.coderman.rpc.transport.netty.codec;

import java.util.List;

import info.coderman.rpc.core.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
/**
 * 
 * @author yuezixin 2016-8-23 14:21:00
 *
 */
public class NettyDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;
    private Serializer serializer;
    
    public NettyDecoder(Class<?> genericClass,Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer=serializer;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[dataLength];
        in.readBytes(bytes);
        out.add(serializer.deserialize(bytes,genericClass));
    }
}
