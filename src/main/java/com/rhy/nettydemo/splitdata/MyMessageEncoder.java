package com.rhy.nettydemo.splitdata;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 13:53:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: 自定义编码器
 */
public class MyMessageEncoder extends MessageToByteEncoder<MyMessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageToByteEncoder#encode:进行数据编码");
        //写入数据长度
        out.writeInt(msg.getSize());
        //写入数据内容
        out.writeBytes(msg.getData());
    }
}
