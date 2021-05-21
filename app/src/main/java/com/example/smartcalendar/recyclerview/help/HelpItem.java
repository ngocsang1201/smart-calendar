package com.example.smartcalendar.recyclerview.help;

public class HelpItem {

    private String title;
    private String desc;
    private int icon;
    private int color;

    public HelpItem(String title, String desc, int icon, int color) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
