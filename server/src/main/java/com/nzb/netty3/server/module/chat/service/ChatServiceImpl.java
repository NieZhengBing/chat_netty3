package com.nzb.netty3.server.module.chat.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.nzb.netty3.common.core.exception.ErrorCodeException;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.core.session.SessionManager;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.common.module.chat.ChatCmd;
import com.nzb.netty3.common.module.chat.response.ChatResponse;
import com.nzb.netty3.common.module.chat.response.ChatType;
import com.nzb.netty3.server.module.player.dao.PlayerDao;
import com.nzb.netty3.server.module.player.dao.entity.Player;

public class ChatServiceImpl implements ChatService {
	@Autowired
	private PlayerDao playerDao;

	@Override
	public void publicChat(long playerId, String content) {
		Player player = playerDao.getPlayerById(playerId);
		Set<Long> onlinePlayers = SessionManager.getOnlinePlayers();
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setCharType(ChatType.PUBLIC_CHAT);
		chatResponse.setSendPlayerId(player.getPlayerId());
		chatResponse.setSendPlayerName(player.getPlayerName());
		chatResponse.setMessage(content);

		for (long targetPlayerId : onlinePlayers) {
			SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		}
	}

	@Override
	public void privateChat(long playerId, long targetPlayerId, String content) {
		if (playerId == targetPlayerId) {
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}
		Player player = playerDao.getPlayerById(playerId);
		Player targetPlayer = playerDao.getPlayerById(targetPlayerId);
		if (targetPlayer == null) {
			throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
		}

		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setCharType(ChatType.PRIVATE_CHAT);
		chatResponse.setSendPlayerId(player.getPlayerId());
		chatResponse.setTargetPlayerName(targetPlayer.getPlayerName());
		chatResponse.setSendPlayerName(player.getPlayerName());
		chatResponse.setMessage(content);

		SessionManager.sendMessage(targetPlayerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		SessionManager.sendMessage(playerId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);

	}

}
