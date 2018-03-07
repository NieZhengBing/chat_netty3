package com.nzb.netty3.client.module.player.handler;

import com.nzb.netty3.common.core.annotion.SocketCommand;
import com.nzb.netty3.common.core.annotion.SocketModule;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.player.PlayerCmd;

@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {

	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public void registerAndLogin(int resultCode, byte[] data);

	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public void login(int resultCode, byte[] data);

}
