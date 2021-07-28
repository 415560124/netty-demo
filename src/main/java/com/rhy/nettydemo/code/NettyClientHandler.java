package com.rhy.nettydemo.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 09:54:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //发送二进制
//        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);
//        channel.writeAndFlush(byteBuf);
        //发送字符串
//        channel.writeAndFlush("Hello World!");
//        System.out.println("发送了消息：Hello World!");
        //发送对象 必须实现Serializable接口
//        CommonResult commonResult = new CommonResult(200,"Hello World!");
//        channel.writeAndFlush(commonResult);
        //protostuff
        byte[] serializer = ProtostuffUtil.serializer(new CommonResult(200, "Hello World!"));
        ByteBuf byteBuf = Unpooled.copiedBuffer(serializer);
        channel.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
