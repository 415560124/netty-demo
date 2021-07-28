package com.rhy.nettydemo.splitdata;

/**
 * @author: Herion Lemon
 * @date: 2021年07月28日 13:53:00
 * @slogan: 如果你想攀登高峰，切莫把彩虹当梯子
 * @description:
 */
public class MyMessageProtocol {

    public MyMessageProtocol() {
    }

    public MyMessageProtocol(int size, byte[] data) {
        this.size = size;
        this.data = data;
    }

    private int size;
    private byte[] data;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
