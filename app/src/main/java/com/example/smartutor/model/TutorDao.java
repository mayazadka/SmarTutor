package com.example.smartutor.model;

import androidx.lifecycle.LiveData;
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
    @Transaction @Query("SELECT * FROM Tutor") LiveData<List<Tutor>> getTutors();
    @Query("SELECT * FROM Tutor WHERE email = :email") LiveData<Tutor> getTutor(String email);
    @Query("DELETE FROM Student") void deleteAll();
}
