package com.nzb.netty3.server.module.player.service;

import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.module.player.response.PlayerResponse;

public interface PlayerService {

	public PlayerResponse registerAndLogin(Session session, String playerName, String password);

	public PlayerResponse login(Session session, String playerName, String password);

}
