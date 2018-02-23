package com.nzb.netty3.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.nzb.netty3.common.core.model.Request;

public class RequestDecoder extends FrameDecoder {

	public static int BASE_LENGTH = 4 + 2 + 2 + 4;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() >= BASE_LENGTH) {
			int beginIndex;
			while (true) {
				beginIndex = buffer.readerIndex();
				buffer.markReaderIndex();
				if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
					break;
				}
				buffer.resetReaderIndex();
				buffer.readByte();

				if (buffer.readableBytes() < BASE_LENGTH) {
					return null;
				}
			}

			short module = buffer.readShort();
			short cmd = buffer.readShort();

			int length = buffer.readInt();
			if (length < 0) {
				channel.close();
			}
			if (buffer.readableBytes() < length) {
				buffer.readerIndex(beginIndex);
				return null;
			}

			byte[] data = new byte[length];
			buffer.readBytes(data);

			Request message = new Request();
			message.setModule(module);
			message.setCmd(cmd);
			message.setData(data);
			return message;
		}

		return null;
	}

}
