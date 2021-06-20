package com.example.smartutor.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) void insertLesson(Lesson lesson);
    @Update void updateLesson(Lesson lesson);
    @Delete void deleteLesson(Lesson lesson);
    @Query("SELECT * FROM Lesson") List<Lesson> getLessons();

}
