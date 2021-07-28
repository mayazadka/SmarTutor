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

@Entity(primaryKeys = {Lesson.STUDENT_EMAIL, Lesson.TUTOR_EMAIL, Lesson.DATE})
public class Lesson {
    @NonNull private String studentEmail;
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;
    private Profession subject;
    private String id;
    private Long lastUpdated;
    private Boolean isDeleted;

    //strings
    public static final String STUDENT_EMAIL = "studentEmail";
    public static final String TUTOR_EMAIL = "tutorEmail";
    public static final String DATE = "date";
    public static final String SUBJECT = "subject";
    public static final String ID = "id";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String IS_DELETED = "isDeleted";
    public static final String LESSONS = "lessons";
    public static final String TAG = "TAG";
    public static final String LESSON_LAST_UPDATE = "LessonLastUpdate";

    public Lesson(){}
    public Lesson(String studentEmail, String tutorEmail, LocalDateTime date, Profession subject) {
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.subject = subject;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Lesson(Map<String, Object> json){
        studentEmail =              (String)json.get(STUDENT_EMAIL);
        tutorEmail =                (String)json.get(TUTOR_EMAIL);
        date =                      Converters.fromStringToLocalDateTime((String)json.get(DATE));
        subject =                   Converters.fromStringToProfession((String)json.get(SUBJECT));
        id =                        (String)json.get(ID);
        Timestamp ts =              (Timestamp)json.get(LAST_UPDATED);
        if(ts!=null)                {lastUpdated =ts.getSeconds();}
        else                        {lastUpdated = Long.valueOf(0);}
        isDeleted =                 (Boolean)json.get(IS_DELETED);
    }

    public String getStudentEmail()                     {return studentEmail;}
    public void setStudentEmail(String studentEmail)    {this.studentEmail = studentEmail;}
    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}
    public Profession getSubject()                      {return subject;}
    public void setSubject(Profession subject)          {this.subject = subject;}
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
        Lesson lesson = (Lesson) o;
        return studentEmail.equals(lesson.studentEmail) &&
                tutorEmail.equals(lesson.tutorEmail) &&
                date.equals(lesson.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(studentEmail, tutorEmail, date);
    }

    public Map<String, Object> toJson(){
        Map<String, Object> data = new HashMap<>();
        data.put(STUDENT_EMAIL, studentEmail);
        data.put(TUTOR_EMAIL, tutorEmail);
        data.put(DATE,  Converters.fromLocalDateTimeToString(date));
        data.put(SUBJECT,  Converters.fromProfessionToString(subject));
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
        editor.putLong(LESSON_LAST_UPDATE, timeStamp);
        editor.commit();
    }
    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getLong(LESSON_LAST_UPDATE, 0);
    }
}
