package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {"tutorEmail", "date"})
public class Event {
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;
    private Long id;
    private Long lastUpdated;
    private Boolean isDeleted;

    public Event(){}
    public Event(String tutorEmail, LocalDateTime date, Long id) {
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.id = id;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Event(Map<String, Object> json){
        tutorEmail =                (String)json.get("tutorEmail");
        date =                      Converters.fromStringToLocalDateTime((String)json.get("date"));
        id =                        (long)json.get("id");
        Timestamp ts =              (Timestamp)json.get("lastUpdated");
        if(ts!=null)                {lastUpdated =ts.getSeconds();}
        else                        {lastUpdated = Long.valueOf(0);}
        isDeleted =                 (Boolean)json.get("isDeleted");
    }

    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}
    public Long getId()                                 {return id;}
    public void setId(Long id)                          {this.id = id;}
    public Long getLastUpdated()                        {return lastUpdated;}
    public void setLastUpdated(Long lastUpdated)        {this.lastUpdated = lastUpdated;}
    public Boolean getDeleted()                         {return isDeleted;}
    public void setDeleted(Boolean deleted)             {isDeleted = deleted;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return  tutorEmail.equals(event.tutorEmail) &&
                date.equals(event.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tutorEmail, date);
    }

    public Map<String, Object> toJson(){
        Map<String, Object> data = new HashMap<>();
        data.put("tutorEmail", tutorEmail);
        data.put("date",  Converters.fromLocalDateTimeToString(date));
        data.put("id", id);
        data.put("lastUpdated", FieldValue.serverTimestamp());
        data.put("isDeleted", isDeleted);

        return data;
    }

    public void update(){
        this.lastUpdated = Timestamp.now().getSeconds();
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong("EventLastUpdate", timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("EventLastUpdate", 0);
    }
}
