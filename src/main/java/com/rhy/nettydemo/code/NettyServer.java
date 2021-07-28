package com.rhy.nettydemo.code;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author: Herion Lemon
 * @date: 2021年07月27日 16:35:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //接收客户端来的消息，入站事件，需要解码器
//                        pipeline.addLast(new StringDecoder());
//                        pipeline.addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        System.out.println("Netty server starting...");
        ChannelFuture future = bootstrap.bind(2000).sync();
        future.channel().closeFuture().sync();
    }
}
