package com.nzb.netty3.client.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;

import com.nzb.netty3.client.swing.ResultCodeTip;
import com.nzb.netty3.client.swing.Swingclient;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.module.chat.response.ChatResponse;
import com.nzb.netty3.common.module.chat.response.ChatType;

public class ChatHandlerImpl implements ChatHandler {

	@Autowired
	private Swingclient swingclient;

	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void publicChat(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			swingclient.getTips().setText("send message success");
		} else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void privateChat(int resultCode, byte[] data) {
		if (resultCode == ResultCode.SUCCESS) {
			swingclient.getTips().setText("send message success");
		} else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void receiveveMessage(int resultCode, byte[] data) {
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.readFromBytes(data);

		if (chatResponse.getCharType() == ChatType.PUBLIC_CHAT) {
			StringBuilder builder = new StringBuilder();
			builder.append(chatResponse.getSendPlayerName());
			builder.append("[");
			builder.append(chatResponse.getSendPlayerId());
			builder.append("]");
			builder.append(" say: \n\t");
			builder.append(chatResponse.getMessage());
			builder.append("\n\n");
			swingclient.getChatContext().append(builder.toString());
		} else if (chatResponse.getCharType() == ChatType.PRIVATE_CHAT) {
			StringBuilder builder = new StringBuilder();
			if (swingclient.getPlayerResponse().getPlayerId() == chatResponse.getSendPlayerId()) {
				builder.append("you quite to: ");
				builder.append("[");
				builder.append(chatResponse.getTargetPlayerName());
				builder.append("]");
				builder.append(": \n\t");
				builder.append(chatResponse.getMessage());
				builder.append("\n\n");
			} else {
				builder.append(chatResponse.getSendPlayerName());
				builder.append("[");
				builder.append(chatResponse.getSendPlayerId());
				builder.append("]");
				builder.append(" quite say:\n\t");
				builder.append(chatResponse.getMessage());
				builder.append("\n\n");
			}

			swingclient.getChatContext().append(builder.toString());

		}
	}

}
