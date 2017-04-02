package com.bink.entity;

/**
 * Created by chenbinghao on 16/8/27.
 */
public enum RoleType {

    system(),

    manager(),

    user();

    private int type;

    private int right;

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
