package com.nzb.netty3.common.module.chat.request;

import com.nzb.netty3.common.core.serial.Serializer;

public class PrivateChatRequest extends Serializer {

	private long targetPlayerId;
	private String context;

	public long getTargetPlayerId() {
		return targetPlayerId;
	}

	public void setTargetPlayerId(long targetPlayerId) {
		this.targetPlayerId = targetPlayerId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.targetPlayerId = readLong();
		this.context = readString();
	}

	@Override
	protected void write() {
		writeLong(targetPlayerId);
		writeString(context);
	}

}
