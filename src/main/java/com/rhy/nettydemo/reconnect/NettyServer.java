package com.rhy.nettydemo.reconnect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 15:14
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: Netty服务端
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建两个线程组bossGroup和workerGroup，不指定线程数的话，默认是cpu核数的两倍
        //bossGroup用来处理客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup用来进行业务处理，读写数据
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务器端启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                /**
                 * NioServerSocketChannel作为服务器的通道实现：异步的服务器端 TCP Socket 连接
                 * 每种通道实现负责处理一种网络模型或协议
                 */
                .channel(NioServerSocketChannel.class)
                /**
                 * 初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理，所以同一时间只能处理一个客户端连接
                 * 多个客户端同时进来的时候，服务端将不能处理的客户端连接请求放到队列中
                 */
                .option(ChannelOption.SO_BACKLOG,1024)
                //创建通道初始化对象，设置初始化参数
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        //对workerGroup的SocketChannel设置处理器
                        channel.pipeline().addLast(new NettyServerHandler());
                    }
                });
            System.out.println("Netty服务端启动中");
            //绑定一个端口并同步启动，sync方法是等待异步操作完成
            Integer serverPort = 9000;
            ChannelFuture channelFuture = bootstrap.bind(serverPort).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("Netty服务端，端口"+serverPort+"启动成功");
                    }else{
                        System.out.println("Netty服务端，端口"+serverPort+"启动失败");
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
    }
}
