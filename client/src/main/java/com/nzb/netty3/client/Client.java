package com.nzb.netty3.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nzb.netty3.client.swing.Swingclient;
import com.nzb.netty3.common.core.codc.RequestEncoder;
import com.nzb.netty3.common.core.codc.ResponseDecoder;
import com.nzb.netty3.common.core.model.Request;

@Component
public class Client {
	@Autowired
	private Swingclient swingclint;

	ClientBootstrap bootstrap = new ClientBootstrap();

	private Channel channel;

	private ExecutorService boss = Executors.newCachedThreadPool();
	private ExecutorService worker = Executors.newCachedThreadPool();

	@PostConstruct
	public void init() {
		bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new ResponseDecoder());
				pipeline.addLast("encoder", new RequestEncoder());
				pipeline.addLast("hiHandler", new ClientHandler(swingclint));
				return pipeline;
			}
		});
	}

	public void connect() throws InterruptedException {
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
		connect.sync();
		connect.getChannel();
	}

	public void shutdown() {
		bootstrap.shutdown();
	}

	public Channel getChannel() {
		return channel;
	}

	public void sendRequest(Request request) throws InterruptedException {
		if (channel == null || !channel.isConnected()) {
			connect();
		}

		channel.write(request);
	}

}
