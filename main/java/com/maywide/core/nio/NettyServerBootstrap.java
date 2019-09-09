package com.maywide.core.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.maywide.core.nio.msg.CommonMsg;
import com.maywide.core.util.PropertyUtil;

public class NettyServerBootstrap {
	private int port;
	private SocketChannel socketChannel;
	private static ObjectMapper jsonMapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(NettyServerBootstrap.class);
	
	public NettyServerBootstrap() throws InterruptedException {
		int port = new Integer(PropertyUtil.getValueFromProperites("sysconfig", "nio.port"));
		this.port = port;
		bind();
	}

	public NettyServerBootstrap(int port) throws InterruptedException {
		this.port = port;
		bind();
	}

	private void bind() throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		// 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// 保持长连接状态
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel)
					throws Exception {
				ChannelPipeline p = socketChannel.pipeline();
				p.addLast(new StringDecoder(Charset.forName("UTF-8")));
				p.addLast(new StringEncoder(Charset.forName("UTF-8")));
				p.addLast(new NettyServerHandler());
			}
		});
		ChannelFuture f = bootstrap.bind(port).sync();
		if (f.isSuccess()) {
			logger.info("server start---------------");
		}
	}
	
	public static void sendAllClient(CommonMsg askMsg) {
		String msg = null;
		try {
			msg = jsonMapper.writeValueAsString(askMsg);
		} catch (Exception e) {
			logger.error("sendAllClient askMsg error：", e);
		}
		
		if (msg != null) {
			for (SocketChannel channel : NettyChannelMap.getMap().values()) {
				channel.writeAndFlush(msg);
			}
		}
	}
	
	public static void sendAllClient(String clientId, CommonMsg askMsg) {
		String msg = null;
		try {
			msg = jsonMapper.writeValueAsString(askMsg);
		} catch (Exception e) {
			logger.error("sendAllClient askMsg error：", e);
		}
		logger.info("微信发送支付通知：sendAllClient ,msg = " + msg);
		if (msg != null) {
			for (String key : NettyChannelMap.getMap().keySet()) {
				logger.info("key = " + key);
				if (clientId.equals(key)) {
					logger.info("微信发送支付通知：clientId = " + clientId + ",msg = " + msg);
					SocketChannel channel = NettyChannelMap.getMap().get(key);
					channel.writeAndFlush(msg);
				}
			}
		}
	}
	
	public static void sendAllClient(String clientId,Object object,String cmdtype,String type) throws JSONException{
		String content = JSONUtil.serialize(object);
		CommonMsg msg = new CommonMsg();
		msg.setClientId(clientId);
		msg.setCmdtype(cmdtype);
		msg.setType(type);
		msg.setContent(content);
		sendAllClient(clientId, msg);
	}
	
}
