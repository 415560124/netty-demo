package com.rhy.nettydemo.code;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 09:53:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap()
                .group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //给服务端发送属于出站事件，需要编码器
//                        pipeline.addLast(new StringEncoder());
//                        pipeline.addLast(new ObjectEncoder());
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 2000).sync();
        future.channel().closeFuture().sync();
    }
}
