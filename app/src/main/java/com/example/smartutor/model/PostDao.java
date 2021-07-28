package com.example.smartutor.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) void insertPost(Post post);
    @Update void updatePost(Post post);
    @Delete void deletePost(Post post);
    @Query("SELECT * FROM Post") LiveData<List<Post>> getPosts();
    @Query("SELECT * FROM Post WHERE tutorEmail = :email") LiveData<List<Post>> getPostsByTutor(String email);
    @Query("SELECT * FROM Post WHERE id = :id") LiveData<Post> getPostById(String id);
    @Query("DELETE FROM Post WHERE tutorEmail = :email") void deleteByTutor(String email);
    @Query("DELETE FROM Post") void deleteAll();

}
