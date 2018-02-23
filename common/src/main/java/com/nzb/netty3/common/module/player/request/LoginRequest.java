package com.nzb.netty3.common.module.player.request;

import com.nzb.netty3.common.core.serial.Serializer;

public class LoginRequest extends Serializer {

	private String playerName;
	private String password;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected void read() {
		this.playerName = readString();
		this.password = readString();
	}

	@Override
	protected void write() {
		writeString(playerName);
		writeString(password);
	}

}
