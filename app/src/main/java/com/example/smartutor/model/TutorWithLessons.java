package com.example.smartutor.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TutorWithLessons {
    @Embedded
    private Tutor tutor;
    @Relation(
            parentColumn = "email",
            entityColumn = "tutorEmail"
    )
    private List<Lesson> lessons;

    public TutorWithLessons(){}

    public Tutor getTutor()                         {return tutor;}
    public void setTutor(Tutor tutor)               {this.tutor = tutor;}
    public List<Lesson> getLessons()                {return lessons;}
    public void setLessons(List<Lesson> lessons)    {this.lessons = lessons;}
}
