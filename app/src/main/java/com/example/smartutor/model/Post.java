package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {Post.ID})
public class Post {
    @NonNull
    private String id;
    private String tutorEmail;
    private String text;
    private String picture;
    private Long lastUpdated;
    private Boolean isDeleted;


    //strings
    public static final String ID = "id";
    public static final String TUTOR_EMAIL = "tutorEmail";
    public static final String TEXT = "text";
    public static final String PICTURE = "picture";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String IS_DELETED = "isDeleted";
    public static final String POSTS = "posts";
    public static final String TAG = "TAG";
    public static final String POST_LAST_UPDATE = "PostLastUpdate";
    public static final String PICTURES_FOLDER = "pictures";


    public Post() {}
    public Post(String tutorEmail, String text, String picture) {
        this.tutorEmail = tutorEmail;
        this.text = text;
        this.picture = picture;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Post(Map<String, Object> json){
        tutorEmail =                (String)json.get(TUTOR_EMAIL);
        text =                      (String)json.get(TEXT);
        picture =                   (String)json.get(PICTURE);
        id =                        (String)json.get(ID);
        Timestamp ts =              (Timestamp)json.get(LAST_UPDATED);
        if(ts!=null)                {lastUpdated =ts.getSeconds();}
        else                        {lastUpdated = Long.valueOf(0);}
        isDeleted =                 (Boolean)json.get(IS_DELETED);
    }

    public String getId()                               { return id; }
    public String getTutorEmail()                       { return tutorEmail; }
    public String getText()                             { return text; }
    public String getPicture()                          { return picture; }
    public void setId(String id)                        { this.id = id; }
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
        data.put(TUTOR_EMAIL, tutorEmail);
        data.put(TEXT, text);
        data.put(PICTURE, picture);
        data.put(ID, id);
        data.put(LAST_UPDATED, FieldValue.serverTimestamp());
        data.put(IS_DELETED, isDeleted);

        return data;
    }

    public void update(){
        this.lastUpdated = Timestamp.now().getSeconds();
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(POST_LAST_UPDATE, timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getLong(POST_LAST_UPDATE, 0);
    }
}
