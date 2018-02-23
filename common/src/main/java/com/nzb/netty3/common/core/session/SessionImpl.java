package com.nzb.netty3.common.core.session;

import org.jboss.netty.channel.Channel;

public class SessionImpl implements Session {

	private Channel channel;

	public SessionImpl(Channel channel) {
		super();
		this.channel = channel;
	}

	@Override
	public Object getAttachment() {
		return channel.getAttachment();
	}

	@Override
	public void setAttachment(Object attachment) {
		channel.setAttachment(attachment);
	}

	@Override
	public void removeAttachment() {
		channel.setAttachment(null);
	}

	@Override
	public void write(Object message) {
		channel.write(message);
	}

	@Override
	public boolean isConnected() {
		return channel.isConnected();
	}

	@Override
	public void close() {
		channel.close();
	}

}
