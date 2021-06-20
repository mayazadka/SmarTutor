package com.example.smartutor.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) void insertTutor(Tutor tutor);
    @Update void updateTutor(Tutor tutor);
    @Delete void deleteTutor(Tutor tutor);
    @Transaction @Query("SELECT * FROM Tutor") List<Tutor> getTutors();
    @Query("SELECT * FROM Tutor WHERE email = :email") TutorWithLessons getTutor(String email);
    @Query("SELECT * FROM Tutor WHERE firstName || lastName LIKE '%' || :name || '%'") List<Tutor> getTutorsByName(String name);
    @Query("SELECT COUNT(*) FROM Tutor WHERE email = :email and password = :password") int countMatchTutor(String email, String password);
}
