package com.example.smartutor.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insertEvent(Event event);
    @Query("SELECT * FROM Event") LiveData<List<Event>> getEvents();
    @Query("SELECT * FROM Event WHERE tutorEmail = :tutorEmail and date = :date") LiveData<Event> getEvent(String tutorEmail, LocalDateTime date);

    @Delete void deleteEvent(Event event);
    @Query("SELECT * FROM Event WHERE tutorEmail = :email") LiveData<List<Event>> getEventsByTutor(String email);
    @Query("DELETE FROM Event") void deleteAll();

}
