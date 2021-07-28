package com.rhy.nettydemo.splitdata;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 13:58:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description: 自定义解码器
 */
public class MyMessageDecoder extends ByteToMessageDecoder {
    int size = 0;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageToByteEncoder#decoder:进行数据解码");
        //数据长度已传入
        if(in.readableBytes() > 4){
            if(size == 0){
                size = in.readInt();
            }
            //可能管道中数据未传输完，需要等待
            if(in.readableBytes() < size){
                System.out.println("MessageToByteEncoder#decoder:当前可读数据不足，需要等待...");
                return;
            }
            //内容byte数组
            byte[] content = new byte[size];
            //读取数组长度内容的数据
            in.readBytes(content);
            MyMessageProtocol myMessageProtocol = new MyMessageProtocol(size,content);
            //写入传输数据
            out.add(myMessageProtocol);
            size = 0;
        }
    }
}
