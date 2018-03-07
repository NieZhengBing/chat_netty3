package com.nzb.netty3.client.module.chat.handler;

import com.nzb.netty3.common.core.annotion.SocketCommand;
import com.nzb.netty3.common.core.annotion.SocketModule;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.chat.ChatCmd;

@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {

	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public void publicChat(int resultCode, byte[] data);

	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public void privateChat(int resultCode, byte[] data);

	@SocketCommand(cmd = ChatCmd.PUSHCHAT)
	public void receiveveMessage(int resultCode, byte[] data);

}
