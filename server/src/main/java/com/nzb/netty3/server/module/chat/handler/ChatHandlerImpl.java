package com.nzb.netty3.server.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.nzb.netty3.common.core.exception.ErrorCodeException;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.module.chat.request.PrivateChatRequest;
import com.nzb.netty3.common.module.chat.request.PublicChatRequest;
import com.nzb.netty3.server.module.chat.service.ChatService;

public class ChatHandlerImpl implements ChatHandler {

	@Autowired
	private ChatService chatService;

	@Override
	public Result<?> publicChat(long playerId, byte[] data) {
		try {
			PublicChatRequest request = new PublicChatRequest();
			request.readFromBytes(data);

			if (StringUtils.isEmpty(request.getContext())) {
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}

			chatService.publicChat(playerId, request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

	@Override
	public Result<?> privateChat(long playerId, byte[] data) {
		try {
			PrivateChatRequest request = new PrivateChatRequest();
			request.readFromBytes(data);

			if (StringUtils.isEmpty(request.getContext()) || request.getTargetPlayerId() <= 0) {
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}

			chatService.privateChat(playerId, request.getTargetPlayerId(), request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

}
