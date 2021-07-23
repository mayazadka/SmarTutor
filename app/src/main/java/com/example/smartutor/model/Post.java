package com.example.smartutor.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {"id"})
public class Post {
    private Long id;
    private String tutorEmail;
    private String text;
    private String picture;

    public Post() {}
    public Post(String tutorEmail, String text, String picture) {
        this.tutorEmail = tutorEmail;
        this.text = text;
        this.picture = picture;
    }
    public Post(Map<String, Object> json){
        tutorEmail =              (String)json.get("tutorEmail");
        text =                    (String)json.get("text");
        picture =                 (String)json.get("picture");
        id =                      (long)json.get("id");
    }

    public Long getId()                             { return id; }
    public String getTutorEmail()                   { return tutorEmail; }
    public String getText()                         { return text; }
    public String getPicture()                      { return picture; }
    public void setId(Long id)                      { this.id = id; }
    public void setTutorEmail(String tutorEmail)    { this.tutorEmail = tutorEmail; }
    public void setText(String text)                { this.text = text; }
    public void setPicture(String picture)          { this.picture = picture;}

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

    public Map<String, Object> toJson(){
        Map<String, Object> data = new HashMap<>();
        data.put("tutorEmail", tutorEmail);
        data.put("text", text);
        data.put("picture", picture);
        data.put("id", id);
        return data;
    }
}
