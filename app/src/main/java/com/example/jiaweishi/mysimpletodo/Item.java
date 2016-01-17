package com.example.jiaweishi.mysimpletodo;

/**
 * Created by jiaweishi on 1/14/16.
 */
public class Item {
    private String title;
    private String content;
    private String priority;

    public Item(String title, String priority){
        this.title = title;
        this.priority = priority;
    }

    public Item(String title, String content, String priority){
        this.title = title;
        this.content = content;
        this.priority = priority;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public String getPriority(){
        return this.priority;
    }
}
