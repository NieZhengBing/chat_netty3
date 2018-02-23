package com.nzb.netty3.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.nzb.netty3.common.core.codc.RequestDecoder;
import com.nzb.netty3.common.core.codc.ResponseEncoder;

public class Server {
	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap();

		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();

		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new RequestDecoder());
				pipeline.addLast("encoder", new ResponseEncoder());
				pipeline.addLast("helloHandler", new ServerHandler());
				return pipeline;
			}
		});

		bootstrap.setOption("backlog", 1024);
		bootstrap.bind(new InetSocketAddress(10102));

		System.out.println("start!!!");
	}

}
