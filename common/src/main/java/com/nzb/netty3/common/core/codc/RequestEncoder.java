package com.nzb.netty3.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.nzb.netty3.common.core.model.Request;

public class RequestEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Request message = (Request) msg;
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		buffer.writeShort(message.getModule());
		buffer.writeShort(message.getCmd());
		int length = message.getData() == null ? 0 : message.getData().length;
		if (length <= 0) {
			buffer.writeInt(length);
		} else {
			buffer.writeInt(length);
			buffer.writeBytes(message.getData());
		}
		return buffer;
	}

}
