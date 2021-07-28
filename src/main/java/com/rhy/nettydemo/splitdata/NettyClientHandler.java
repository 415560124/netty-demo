package com.rhy.nettydemo.splitdata;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 11:25:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 50; i++) {
            //特殊字符分割
//            ctx.channel().writeAndFlush("Hello World!_|_");
            //自定义报文
            String data = "Hello World!";
            ctx.channel().writeAndFlush(new MyMessageProtocol(data.getBytes(CharsetUtil.UTF_8).length,data.getBytes(CharsetUtil.UTF_8)));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
