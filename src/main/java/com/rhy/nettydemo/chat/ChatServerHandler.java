package com.rhy.nettydemo.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 16:23
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class ChatServerHandler extends SimpleChannelInboundHandler {
    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[ 客户端 ]"+channel.remoteAddress()+"已上线");
        channelGroup.forEach((channelTemp)->{
            ByteBuf byteBuf = Unpooled.copiedBuffer("[ 客户端 ]" + channel.remoteAddress() + "已上线", CharsetUtil.UTF_8);
            channelTemp.writeAndFlush(byteBuf);
        });
        channelGroup.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[ 客户端 ]"+channel.remoteAddress()+"已下线");
        channelGroup.forEach((channelTemp)->{
            ByteBuf byteBuf = Unpooled.copiedBuffer("[ 客户端 ]" + channel.remoteAddress() + "已上线", CharsetUtil.UTF_8);
            channelTemp.writeAndFlush(byteBuf);
        });
        channelGroup.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        System.out.println(ctx.channel().remoteAddress()+"状态：异常");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel channel = channelHandlerContext.channel();
        System.out.println("[ 客户端 ]"+channel.remoteAddress()+"发送消息："+o);
        channelGroup.forEach((channelTemp)->{
            ByteBuf byteBuf = null;
            if(channelTemp != channel){
                byteBuf = Unpooled.copiedBuffer("[ 客户端 ]"+channel.remoteAddress()+"发送消息："+o, CharsetUtil.UTF_8);
            }else{
                byteBuf = Unpooled.copiedBuffer("[ 自己 ]"+channel.remoteAddress()+"发送消息："+o, CharsetUtil.UTF_8);
            }
            channelTemp.writeAndFlush(byteBuf);
        });
    }
}
