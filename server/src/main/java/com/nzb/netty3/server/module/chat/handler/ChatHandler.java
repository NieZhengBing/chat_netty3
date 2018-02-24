package com.nzb.netty3.server.module.chat.handler;

import com.nzb.netty3.common.core.annotion.SocketCommand;
import com.nzb.netty3.common.core.annotion.SocketModule;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.chat.ChatCmd;

@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {

	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public Result<?> publicChat(long playerId, byte[] data);

	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public Result<?> privateChat(long playerId, byte[] data);

}
