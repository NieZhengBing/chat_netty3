package com.nzb.netty3.server.module.player.handler;

import com.nzb.netty3.common.core.annotion.SocketCommand;
import com.nzb.netty3.common.core.annotion.SocketModule;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.player.PlayerCmd;
import com.nzb.netty3.common.module.player.response.PlayerResponse;

@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {

	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data);

	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public Result<PlayerResponse> login(Session session, byte[] data);

}
