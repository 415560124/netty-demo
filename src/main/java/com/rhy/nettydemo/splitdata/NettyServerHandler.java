package com.rhy.nettydemo.splitdata;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 11:12:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(msg.toString());
        //自定义解码器
        MyMessageProtocol msg1 = (MyMessageProtocol) msg;
        System.out.println("数据长度："+msg1.getSize());
        System.out.println("数据内容："+new String(msg1.getData(),CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
