package com.example.wudelin.cstudy.listviewadapter;

/**
 * Created by wudelin on 2017/11/27.
 */

public class Item {
    private int itemImageId;

    private String itemText;

    private int itemIcon;

    public Item(int itemImageId, String itemText, int itemIcon) {
        this.itemImageId = itemImageId;
        this.itemText = itemText;
        this.itemIcon = itemIcon;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(int itemImageId) {
        this.itemImageId = itemImageId;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }
}
