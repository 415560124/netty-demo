package com.rhy.nettydemo.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 15:13:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接："+ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(" ====== > [server] message received : " + msg);
        if ("Heartbeat Packet".equals(msg)) {
            ctx.channel().writeAndFlush("ok");
        } else {
            System.out.println(" 其他信息处理 ... ");
        }
    }
    //读空闲次数
    int readIdleTimes = 0;
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                System.out.println("读空闲");
                eventType = "读空闲";
                readIdleTimes++;
                break;
            case WRITER_IDLE:
                System.out.println("写空闲");
                eventType = "写空闲";
                //不处理
                break;
            case ALL_IDLE:
                System.out.println("读写空闲");
                eventType = "读写空闲";
                break;
        }
        System.out.println("超时事件："+eventType+"-"+ctx.channel().remoteAddress());
        if(readIdleTimes > 3){
            System.out.println("读空闲超过三次，断开连接");
            //空闲关闭连接
            ctx.channel().writeAndFlush("idle close");
            ctx.channel().close();
        }
    }
}
