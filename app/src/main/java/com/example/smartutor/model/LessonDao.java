package com.example.smartutor.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insertLesson(Lesson lesson);
    @Delete void deleteLesson(Lesson lesson);
    @Query("SELECT * FROM Lesson WHERE tutorEmail = :tutorEmail and date = :date Limit 1") LiveData<Lesson> getLessonByTutor(String tutorEmail, LocalDateTime date);
    @Query("SELECT * FROM Lesson WHERE studentEmail = :studentEmail and date = :date Limit 1") LiveData<Lesson> getLessonByStudent(String studentEmail, LocalDateTime date);
    @Query("SELECT * FROM Lesson WHERE studentEmail = :email") LiveData<List<Lesson>> getLessonsByStudent(String email);
    @Query("SELECT * FROM Lesson WHERE tutorEmail = :email") LiveData<List<Lesson>> getLessonsByTutor(String email);
}
