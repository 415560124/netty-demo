package com.rhy.nettydemo.reconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author: Herion Lemon
 * @date: 2021/7/25 15:51
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: Netty客户端
 */
public class NettyClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private EventLoopGroup group;

    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient("localhost", 9000);
        nettyClient.connect();
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {
        //客户端需要一个事件循环组
        group = new NioEventLoopGroup();
        //创建客户端启动对象
        // bootstrap 可重用, 只需在NettyClient实例化的时候初始化即可.
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //加入处理器
                        ch.pipeline().addLast(new NettyClientHandler(NettyClient.this));
                    }
                });
    }

    public void connect() throws Exception {
        System.out.println("netty client start。。");
        //启动客户端去连接服务器端
        ChannelFuture cf = bootstrap.connect(host, port);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    //重连交给后端线程执行
                    future.channel().eventLoop().schedule(() -> {
                        System.err.println("重连服务端...");
                        try {
                            connect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 3000, TimeUnit.MILLISECONDS);
                } else {
                    System.out.println("服务端连接成功...");
                }
            }
        });
        //对通道关闭进行监听
        cf.channel().closeFuture().sync();
    }
}
