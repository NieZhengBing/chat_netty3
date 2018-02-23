package com.nzb.netty3.common.core.serial;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class BufferFactory {
	public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

	public static ChannelBuffer getBuffer() {
		ChannelBuffer dynamicBuffer = ChannelBuffers.dynamicBuffer();
		return dynamicBuffer;
	}

	public static ChannelBuffer getBuffer(byte[] bytes) {
		ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer(bytes);
		return copiedBuffer;
	}

}
