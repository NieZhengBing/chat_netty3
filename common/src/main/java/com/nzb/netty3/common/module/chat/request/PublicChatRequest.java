package com.nzb.netty3.common.module.chat.request;

import com.nzb.netty3.common.core.serial.Serializer;

public class PublicChatRequest extends Serializer {

	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	protected void read() {
		this.context = readString();
	}

	@Override
	protected void write() {
		writeString(this.context);
	}

}
