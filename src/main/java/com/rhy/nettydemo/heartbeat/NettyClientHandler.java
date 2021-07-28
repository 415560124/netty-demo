package com.rhy.nettydemo.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 16:08:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        if(msg != null && msg.equals("idle close")){
            System.out.println("服务端关闭连接，客户端也关闭");
            ctx.channel().closeFuture();
        }
    }
}
