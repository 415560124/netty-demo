package com.rhy.nettydemo.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: Herion Lemon
 * @date: 2021年07月27日 16:27:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"建立了连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            //原生二进制
//            System.out.println("收到消息："+((ByteBuf) msg).toString(CharsetUtil.UTF_8));
            //protostuff
            ByteBuf msg1 = (ByteBuf) msg;
            //创建一个相同大小的byte数组
            byte[] obj = new byte[msg1.readableBytes()];
            //把byte数组读取到你创建的byte数组中
            msg1.readBytes(obj);
            System.out.println("收到消息："+ProtostuffUtil.deserializer(obj,CommonResult.class));
        }else if(msg instanceof String){
            //String类型
            System.out.println("收到消息："+msg.toString());
        }else {
            //Object对象序列化
            //传入User序列化数据
            System.out.println("收到消息："+msg.toString());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
