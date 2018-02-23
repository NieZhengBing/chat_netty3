package com.nzb.netty3.common.module.player.response;

import com.nzb.netty3.common.core.serial.Serializer;

public class PlayerResponse extends Serializer {

	private long playerId;
	private String playerName;
	private int level;
	private int exp;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	protected void read() {
		this.playerId = readLong();
		this.playerName = readString();
		this.level = readInt();
		this.exp = readInt();
	}

	@Override
	protected void write() {
		writeLong(playerId);
		writeString(playerName);
		writeInt(level);
		writeInt(exp);
	}

}
