package com.example.smartutor.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) void insertStudent(Student student);
    @Update void updateStudent(Student student);
    @Delete void deleteStudent(Student student);
    @Transaction @Query("SELECT * FROM Student") List<Student> getStudents();
    @Query("SELECT * FROM Student WHERE email = :email") StudentWithLessons getStudent(String email);
    @Query("SELECT COUNT(*) FROM Student WHERE email = :email and password = :password") int countMatchStudent(String email, String password);


}
