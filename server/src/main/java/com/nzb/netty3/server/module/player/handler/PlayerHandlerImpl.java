package com.nzb.netty3.server.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nzb.netty3.common.core.exception.ErrorCodeException;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.module.player.request.LoginRequest;
import com.nzb.netty3.common.module.player.request.RegisterRequest;
import com.nzb.netty3.common.module.player.response.PlayerResponse;
import com.nzb.netty3.server.module.player.service.PlayerService;

@Component
public class PlayerHandlerImpl implements PlayerHandler {

	@Autowired
	private PlayerService playerService;

	@Override
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
		PlayerResponse result = null;
		try {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.readFromBytes(data);

			if (StringUtils.isEmpty(registerRequest.getPlayerName())
					|| StringUtils.isEmpty(registerRequest.getPassword())) {
				return Result.ERROR(ResultCode.PLAYERNAME_NULL);
			}

			result = playerService.registerAndLogin(session, registerRequest.getPlayerName(),
					registerRequest.getPassword());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUNNCESS(result);
	}

	@Override
	public Result<PlayerResponse> login(Session session, byte[] data) {
		PlayerResponse result = null;

		try {
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.readFromBytes(data);

			if (StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassword())) {
				return Result.ERROR(ResultCode.PLAYERNAME_NULL);
			}

			result = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassword());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}

		return Result.SUNNCESS(result);
	}

}
