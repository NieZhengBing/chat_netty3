package com.nzb.netty3.client.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nzb.netty3.client.swing.ResultCodeTip;
import com.nzb.netty3.client.swing.Swingclient;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.module.player.response.PlayerResponse;

@Component
public class PlayerHandlerImpl implements PlayerHandler {

	@Autowired
	private Swingclient swingclient;

	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void registerAndLogin(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			PlayerResponse playerResponse = new PlayerResponse();
			playerResponse.readFromBytes(data);

			swingclient.setPlayerResponse(playerResponse);
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		} else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void login(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			PlayerResponse playerResponse = new PlayerResponse();
			playerResponse.readFromBytes(data);

			swingclient.setPlayerResponse(playerResponse);
			swingclient.getTips().setText("login success");
		} else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

}
