package com.rhy.nettydemo.chat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 16:34
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class ChatClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println(o);
    }
}
