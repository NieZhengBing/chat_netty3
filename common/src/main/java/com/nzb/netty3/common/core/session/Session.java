package com.nzb.netty3.common.core.session;

public interface Session {

	Object getAttachment();

	void setAttachment(Object attachment);

	void removeAttachment();

	void write(Object message);

	boolean isConnected();

	void close();

}
