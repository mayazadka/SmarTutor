package com.example.smartutor.model;

import java.util.Date;

public class Post {
    private int id;
    private Tutor tutor;
    private String text;
    private String picture;

    public Post(int id, Tutor tutor, String text, String picture) {
        this.id = id;
        this.tutor = tutor;
        this.text = text;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public String getText() {
        return text;
    }
    public String getPicture() {
        return picture;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
