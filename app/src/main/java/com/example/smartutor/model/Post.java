package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {"id"})
public class Post {
    private Long id;
    private String tutorEmail;
    private String text;
    private String picture;
    private Long lastUpdated;
    private Boolean isDeleted;

    public Post() {}
    public Post(String tutorEmail, String text, String picture) {
        this.tutorEmail = tutorEmail;
        this.text = text;
        this.picture = picture;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Post(Map<String, Object> json){
        tutorEmail =              (String)json.get("tutorEmail");
        text =                    (String)json.get("text");
        picture =                 (String)json.get("picture");
        id =                      (long)json.get("id");
        Timestamp ts =              (Timestamp)json.get("lastUpdated");
        if(ts!=null)                {lastUpdated =ts.getSeconds();}
        else                        {lastUpdated = Long.valueOf(0);}
        isDeleted =                 (Boolean)json.get("isDeleted");
    }

    public Long getId()                                 { return id; }
    public String getTutorEmail()                       { return tutorEmail; }
    public String getText()                             { return text; }
    public String getPicture()                          { return picture; }
    public void setId(Long id)                          { this.id = id; }
    public void setTutorEmail(String tutorEmail)        { this.tutorEmail = tutorEmail; }
    public void setText(String text)                    { this.text = text; }
    public void setPicture(String picture)              { this.picture = picture;}
    public Long getLastUpdated()                        {return lastUpdated;}
    public void setLastUpdated(Long lastUpdated)        {this.lastUpdated = lastUpdated;}
    public Boolean getDeleted()                         {return isDeleted;}
    public void setDeleted(Boolean deleted)             {isDeleted = deleted;}

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
        data.put("lastUpdated", FieldValue.serverTimestamp());
        data.put("isDeleted", isDeleted);

        return data;
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("PostLastUpdate", timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostLastUpdate", 0);
    }
}
