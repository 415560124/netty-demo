package com.rhy.nettydemo.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 15:51
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: Netty客户端
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //创建客户端启动对象
        Bootstrap bootstrap = new Bootstrap();
        //设置相关参数
        bootstrap.group(eventLoopGroup)
                //客户端是NioSocketChannel 服务端是NioServerSocketChannel
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        System.out.println("Netty客户端启动中");
        //启动客户端连接服务端
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 2000).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
