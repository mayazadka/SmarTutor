package com.example.smartutor.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity()
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tutorEmail;
    private String text;
    private String picture;

    public Post() {}
    public Post(String tutorEmail, String text, String picture) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return tutorEmail.equals(post.tutorEmail) &&
                text.equals(post.text) &&
                picture.equals(post.picture);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tutorEmail, text, picture);
    }
}
