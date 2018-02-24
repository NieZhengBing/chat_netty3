package com.nzb.netty3.server.module.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nzb.netty3.common.core.exception.ErrorCodeException;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.core.session.SessionManager;
import com.nzb.netty3.common.module.player.response.PlayerResponse;
import com.nzb.netty3.server.module.player.dao.PlayerDao;
import com.nzb.netty3.server.module.player.dao.entity.Player;

@Component
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao playerDao;

	@Override
	public PlayerResponse registerAndLogin(Session session, String playerName, String password) {
		Player existplayer = playerDao.getPlayerByName(playerName);

		if (existplayer != null) {
			throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
		}

		Player player = new Player();
		player.setPlayerName(playerName);
		player.setPassword(password);
		player = playerDao.createPlayer(player);

		return login(session, playerName, password);
	}

	@Override
	public PlayerResponse login(Session session, String playerName, String password) {
		if (session.getAttachment() != null) {
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}

		Player player = playerDao.getPlayerByName(playerName);
		if (player == null) {
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}

		if (!player.getPassword().equals(password)) {
			throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
		}

		boolean onlinePlayer = SessionManager.isOnlinePlayer(player.getPlayerId());
		if (onlinePlayer) {
			Session oldSession = SessionManager.removeSession(player.getPlayerId());
			oldSession.removeAttachment();
			oldSession.close();
		}

		if (SessionManager.putSession(player.getPlayerId(), session)) {
			session.setAttachment(player);
		} else {
			throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
		}

		PlayerResponse playerResponse = new PlayerResponse();
		playerResponse.setPlayerId(player.getPlayerId());
		playerResponse.setPlayerName(player.getPlayerName());
		playerResponse.setLevel(player.getLevel());
		playerResponse.setExp(player.getExp());
		return playerResponse;

	}

}
