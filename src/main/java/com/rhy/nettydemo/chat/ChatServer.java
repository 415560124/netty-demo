package com.rhy.nettydemo.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 16:19
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: 聊天室服务端
 */
public class ChatServer {
    public static void main(String[] args) throws InterruptedException {
        //接受连接的主线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //处理请求的主线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //服务端启动对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("decoder",new StringDecoder());
                        pipeline.addLast("encoder",new StringEncoder());
                        pipeline.addLast(new ChatServerHandler());
                    }
                })
        ;
        System.out.println("聊天室Server启动");
        ChannelFuture channelFuture = serverBootstrap.bind(2000).sync();

        Channel channel = channelFuture.channel();
    }
}
