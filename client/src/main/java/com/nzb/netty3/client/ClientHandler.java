package com.nzb.netty3.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.nzb.netty3.client.scanner.Invoker;
import com.nzb.netty3.client.scanner.InvokerHoler;
import com.nzb.netty3.client.swing.Swingclient;
import com.nzb.netty3.common.core.model.Response;

public class ClientHandler extends SimpleChannelHandler {

	private Swingclient swingClient;

	public ClientHandler(Swingclient swingClient) {
		super();
		this.swingClient = swingClient;
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		swingClient.getTips().setText("server disconnected");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Response response = (Response) e.getMessage();
		handlerResponse(response);
	}

	private void handlerResponse(Response response) {
		Invoker invoker = InvokerHoler.getInvoker(response.getModule(), response.getCmd());
		if (invoker != null) {
			try {
				invoker.invoke(response.getStateCode(), response.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(String.format("module:%s cmd:%s can not find command executor", response.getModule(),
					response.getCmd()));
		}
	}

}
