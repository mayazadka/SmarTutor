package com.example.smartutor.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.smartutor.MyApplication;

@Database(entities = {Student.class, Tutor.class, Lesson.class, Post.class, Event.class}, version = 5)
@TypeConverters({ Converters.class })
abstract class AppLocalDbRepository extends RoomDatabase{
    public abstract StudentDao studentDao();
    public abstract TutorDao tutorDao();
    public abstract LessonDao lessonDao();
    public abstract PostDao postDao();
    public abstract EventDao eventDao();
}

public class AppLocalDB {
    public final static AppLocalDbRepository db =
            Room.databaseBuilder(   MyApplication.context,
                                    AppLocalDbRepository.class,
                                    "dbFileName").fallbackToDestructiveMigration().build();
}
