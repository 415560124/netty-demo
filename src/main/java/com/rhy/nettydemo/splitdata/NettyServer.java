package com.rhy.nettydemo.splitdata;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 11:12:00
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
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //自定义解码器
                        pipeline.addLast(new MyMessageDecoder());
                        //分隔符拆分的解码器
//                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("_|_", CharsetUtil.UTF_8)));
//                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(2000).sync();
        future.channel().closeFuture().sync();
    }
}
