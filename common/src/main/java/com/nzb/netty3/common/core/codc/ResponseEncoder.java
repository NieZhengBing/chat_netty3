package com.nzb.netty3.common.core.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.nzb.netty3.common.core.model.Response;

public class ResponseEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Response response = (Response) msg;
		System.out.println("return response: " + "module: " + response.getModule() + " cmd: " + response.getCmd()
				+ " resultCode: " + response.getStateCode());
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		buffer.writeShort(response.getModule());
		buffer.writeShort(response.getCmd());
		buffer.writeInt(response.getStateCode());

		int length = response.getData() == null ? 0 : response.getData().length;
		if (length <= 0) {
			buffer.writeInt(length);
		} else {
			buffer.writeInt(length);
			buffer.writeBytes(response.getData());
		}
		return buffer;
	}

}
