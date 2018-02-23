package com.nzb.netty3.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.nzb.netty3.common.core.model.Request;
import com.nzb.netty3.common.core.model.Response;
import com.nzb.netty3.common.core.model.Result;
import com.nzb.netty3.common.core.model.ResultCode;
import com.nzb.netty3.common.core.session.Session;
import com.nzb.netty3.common.core.session.SessionImpl;
import com.nzb.netty3.common.module.ModuleId;
import com.nzb.netty3.server.module.player.dao.entity.Player;
import com.nzb.netty3.server.scanner.Invoker;
import com.nzb.netty3.server.scanner.InvokerHoler;

public class ServerHandler extends SimpleChannelHandler {

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
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
		}
	}

}
