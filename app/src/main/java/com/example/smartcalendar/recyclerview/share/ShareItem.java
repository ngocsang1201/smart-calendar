package com.example.smartcalendar.recyclerview.share;

public class ShareItem {

    private String title;
    private int icon;
    private int color;

    public ShareItem(String title, int icon, int color) {
        this.title = title;
        this.icon = icon;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
