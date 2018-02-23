package com.nzb.netty3.common.module.chat.response;

import com.nzb.netty3.common.core.serial.Serializer;

public class ChatResponse extends Serializer {

	private long sendPlayerId;
	private String sendPlayerName;
	private String targetPlayerName;
	private byte charType;
	private String message;

	public long getSendPlayerId() {
		return sendPlayerId;
	}

	public void setSendPlayerId(long sendPlayerId) {
		this.sendPlayerId = sendPlayerId;
	}

	public String getSendPlayerName() {
		return sendPlayerName;
	}

	public void setSendPlayerName(String sendPlayerName) {
		this.sendPlayerName = sendPlayerName;
	}

	public String getTargetPlayerName() {
		return targetPlayerName;
	}

	public void setTargetPlayerName(String targetPlayerName) {
		this.targetPlayerName = targetPlayerName;
	}

	public byte getCharType() {
		return charType;
	}

	public void setCharType(byte charType) {
		this.charType = charType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	protected void read() {
		this.sendPlayerId = readLong();
		this.sendPlayerName = readString();
		this.targetPlayerName = readString();
		this.charType = readByte();
		this.message = readString();
	}

	@Override
	protected void write() {
		writeLong(this.sendPlayerId);
		writeString(this.sendPlayerName);
		writeString(this.targetPlayerName);
		writeByte(this.charType);
		writeString(this.message);
	}

}
