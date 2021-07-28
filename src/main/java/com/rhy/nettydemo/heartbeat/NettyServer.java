package com.rhy.nettydemo.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 15:09:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        //心跳检测 读空闲5秒一次，单位秒
                        //IdleStateHandler#readerIdleTime超过这个事件没有读事件，会触发IdleStateEvent事件，并交给下一个handler处理
                        //下一个handler必须实现userEventTriggered方法
                        pipeline.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        System.out.println("netty server starting");
        ChannelFuture future = serverBootstrap.bind(2000).sync();
        future.channel().closeFuture().sync();
    }
}
