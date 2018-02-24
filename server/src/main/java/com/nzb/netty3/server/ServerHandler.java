package com.nzb.netty3.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.google.protobuf.GeneratedMessage;
import com.nzb.netty3.common.core.model.Request;
import com.nzb.netty3.common.core.model.Response;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.core.serial.Serializer;
import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.core.session.SessionImpl;
import com.nzb.netty3.common.core.session.SessionManager;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.server.module.player.dao.entity.Player;
import com.nzb.netty3.server.scanner.Invoker;
import com.nzb.netty3.server.scanner.InvokerHoler;

public class ServerHandler extends SimpleChannelHandler {

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		SessionImpl session = new SessionImpl(ctx.getChannel());
		Object object = session.getAttachment();
		if (object != null) {
			Player player = (Player) object;
			SessionManager.removeSession(player.getPlayerId());
		}
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
		Request request = (Request) e.getMessage();
		handleMessage(new SessionImpl(ctx.getChannel()), request);
	}

	private void handleMessage(Session session, Request request) {
		Response response = new Response(request);
		System.out.println("module: " + request.getModule() + " " + " cmd: " + request.getCmd());
		Invoker invoker = InvokerHoler.getInvoker(request.getModule(), request.getCmd());
		if (invoker != null) {
			try {
				Result<?> result = null;
				if (request.getModule() == ModuleId.PLAYER) {
					result = (Result<?>) invoker.invoker(session, request.getData());
				} else {
					Object attachment = session.getAttachment();
					if (attachment != null) {
						Player player = (Player) attachment;
						result = (Result<?>) invoker.invoker(player.getPlayerId(), request.getData());
					} else {
						response.setStateCode(ResultCode.LOGIN_PLEASE);
						session.write(response);
						return;
					}
				}

				if (result.getResultCode() == ResultCode.SUCCESS) {
					Object object = result.getContent();
					if (object != null) {
						if (object instanceof Serializer) {
							Serializer content = (Serializer) object;
							response.setData(content.getBytes());
						} else if (object instanceof GeneratedMessage) {
							GeneratedMessage content = (GeneratedMessage) object;
							response.setData(content.toByteArray());
						} else {
							System.out.println(String.format("can not transport object: %s", object));
						}
					}
					session.write(response);
				} else {
					response.setStateCode(result.getResultCode());
					session.write(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
				session.write(response);
			}
		} else {
			response.setStateCode(ResultCode.NO_INVOKER);
			session.write(response);
			return;
		}
	}

}
