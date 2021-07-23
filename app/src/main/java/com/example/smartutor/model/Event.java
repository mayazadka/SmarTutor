package com.example.smartutor.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(primaryKeys = {"tutorEmail", "date"})
public class Event {
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;
    private Long id;

    public Event(){}
    public Event(String tutorEmail, LocalDateTime date, Long id) {
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.id = id;
    }
    public Event(Map<String, Object> json){
        tutorEmail =                (String)json.get("tutorEmail");
        date =                      Converters.fromStringToLocalDateTime((String)json.get("date"));
        id =                        (long)json.get("id");
    }

    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}
    public Long getId()                                 {return id;}
    public void setId(Long id)                          {this.id = id;}

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
        return data;
    }
}
