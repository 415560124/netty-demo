package com.rhy.nettydemo.reconnect;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 15:37
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: Netty服务端处理器
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    public static final Charset UTF_8 = CharsetUtil.UTF_8;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"状态：建立连接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"状态：断开连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf msg1 = (ByteBuf) msg;
        System.out.println(ctx.channel().remoteAddress()+"状态：收到消息;内容："+((ByteBuf) msg).toString(UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Client!", UTF_8);
        System.out.println(ctx.channel().remoteAddress()+"状态：收到消息完毕;返回消息："+byteBuf.toString(UTF_8));
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(ctx.channel().remoteAddress()+"状态：异常");
    }
}
