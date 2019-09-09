package com.maywide.core.nio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maywide.core.nio.msg.CommonMsg;
import com.maywide.core.nio.msg.MsgType;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
	private static ObjectMapper jsonMapper = new ObjectMapper();
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }
	
	@Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		logger.info("receive msg : " + msg);
		CommonMsg commMsg = jsonMapper.readValue(msg, CommonMsg.class);
		
		if(MsgType.LOGIN.toString().equals(commMsg.getType())){
			NettyChannelMap.add(commMsg.getClientId(), (SocketChannel) ctx.channel());
            CommonMsg replyMsg = new CommonMsg();
            replyMsg.setType(MsgType.REPLY.toString());
            ctx.writeAndFlush(jsonMapper.writeValueAsString(replyMsg));
            logger.info("client" + commMsg.getClientId() + " 连接成功");
        } else if(MsgType.PING.toString().equals(commMsg.getType())){
            CommonMsg replyPing = new CommonMsg();
            replyPing.setType(MsgType.REPLY.toString());
            ctx.writeAndFlush(jsonMapper.writeValueAsString(replyPing));
        }  else if(MsgType.REPLY.toString().equals(commMsg.getType())){
        	logger.info("receive client msg : " + commMsg.getContent());
        } 
        
        ReferenceCountUtil.release(msg);
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

