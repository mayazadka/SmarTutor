package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {Event.TUTOR_EMAIL, Event.DATE})
public class Event {
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;
    private String id;
    private Long lastUpdated;
    private Boolean isDeleted;

    //strings
    public static final String TUTOR_EMAIL = "tutorEmail";
    public static final String DATE = "date";
    public static final String ID = "id";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String IS_DELETED = "isDeleted";
    public static final String EVENTS = "events";
    public static final String TAG = "TAG";
    public static final String EVENT_LAST_UPDATE = "EventLastUpdate";


    public Event(){}
    public Event(String tutorEmail, LocalDateTime date) {
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Event(Map<String, Object> json){
        tutorEmail =                (String)json.get(TUTOR_EMAIL);
        date =                      Converters.fromStringToLocalDateTime((String)json.get(DATE));
        id =                        (String)json.get(ID);
        Timestamp ts =              (Timestamp)json.get(LAST_UPDATED);
        if(ts!=null)                {lastUpdated =ts.getSeconds();}
        else                        {lastUpdated = Long.valueOf(0);}
        isDeleted =                 (Boolean)json.get(IS_DELETED);
    }

    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}
    public String getId()                               {return id;}
    public void setId(String id)                        {this.id = id;}
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
        data.put(TUTOR_EMAIL, tutorEmail);
        data.put(DATE,  Converters.fromLocalDateTimeToString(date));
        data.put(ID, id);
        data.put(LAST_UPDATED, FieldValue.serverTimestamp());
        data.put(IS_DELETED, isDeleted);
        return data;
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(EVENT_LAST_UPDATE, timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getLong(EVENT_LAST_UPDATE, 0);
    }
}
