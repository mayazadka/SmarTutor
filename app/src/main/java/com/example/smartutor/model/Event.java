package com.example.smartutor.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(primaryKeys = {"tutorEmail", "date"})
public class Event {
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;

    public Event(){}
    public Event(String tutorEmail, LocalDateTime date) {
        this.tutorEmail = tutorEmail;
        this.date = date;
    }

    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}

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
}
