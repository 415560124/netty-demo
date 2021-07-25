package com.rhy.nettydemo.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 16:25
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ChatClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 2000).sync();
        Channel channel = channelFuture.channel();
        System.out.println("聊天室Client地址："+channel.localAddress());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg = scanner.next();
            channel.writeAndFlush(msg);
        }

    }
}
