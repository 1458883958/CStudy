package com.example.wudelin.cstudy.draw;



public class DrawStep {
    public static final byte MoveTo = 1;
    public static final byte LineTo = 2;
    public static final byte UP = 3;

    private byte type;
    private int xP;

    private int yP;


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getxP() {
        return xP;
    }

    public void setxP(int xP) {
        this.xP = xP;
    }

    public int getyP() {
        return yP;
    }

    public void setyP(int yP) {
        this.yP = yP;
    }
}
