package com.example.smartutor.model;

import java.util.Date;

public class Post {
    private int id;
    private String tutorEmail;
    private String text;
    private String picture;

    public Post(int id, String tutorEmail, String text, String picture) {
        this.id = id;
        this.tutorEmail = tutorEmail;
        this.text = text;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }
    public String getTutorEmail() {
        return tutorEmail;
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
    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
}
