package com.example.smartutor.model;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ModelFireBase {
    private static Long lessonID = Long.valueOf(0);
    private static Long eventID = Long.valueOf(0);

    public ModelFireBase(){
        FirebaseFirestore.getInstance().collection("lessons")
                .orderBy("id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            lessonID = (Long)document.get("id")+1;
                        }
                    } else {}
                });
        FirebaseFirestore.getInstance().collection("events")
                .orderBy("id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            eventID = (Long)document.get("id")+1;
                        }
                    } else {}
                });
    }

    public static void getStudents(Consumer<List<Student>> consumer){
        FirebaseFirestore.getInstance().collection("students")
                .get()
                .addOnCompleteListener(task -> {
                    List<Student> students = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            students.add(new Student(document.getData()));
                        }
                    } else {}
                    consumer.accept(students);
                });
    }
    public static void addStudent(Student student, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection("students").document(student.getEmail())
            .set(student.toJson())
            .addOnSuccessListener(v->listener.onComplete())
            .addOnFailureListener(v->listener.onComplete());
    }
    public static void updateStudent(Student student, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection("students").document(student.getEmail())
                .set(student.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteStudent(Student student, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("students").document(student.getEmail())
                .delete()
                .addOnSuccessListener(aVoid -> listener.onComplete())
                .addOnFailureListener(aVoid -> listener.onComplete());
    }

    public static void getTutors(Consumer<List<Tutor>> consumer){
        FirebaseFirestore.getInstance().collection("tutors")
                .get()
                .addOnCompleteListener(task -> {
                    List<Tutor> tutors = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            tutors.add(new Tutor(document.getData()));
                        }
                    } else {}
                    consumer.accept(tutors);
                });
    }
    public static void addTutor(Tutor tutor, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection("tutors").document(tutor.getEmail())
                .set(tutor.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void updateTutor(Tutor tutor, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection("tutors").document(tutor.getEmail())
                .set(tutor.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteTutor(Tutor tutor, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("tutors").document(tutor.getEmail())
                .delete()
                .addOnSuccessListener(aVoid -> listener.onComplete())
                .addOnFailureListener(aVoid -> listener.onComplete());
    }

    public static void getLessons(Consumer<List<Lesson>> consumer){
        FirebaseFirestore.getInstance().collection("lessons")
                .get()
                .addOnCompleteListener(task -> {
                    List<Lesson> lessons = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            lessons.add(new Lesson(document.getData()));
                        }
                    } else {}
                    consumer.accept(lessons);
                });
    }
    public static void addLesson(Lesson lesson, Model.OnCompleteListener listener){
        lesson.setId(lessonID);
        lessonID++;
        FirebaseFirestore.getInstance().collection("lessons").document(lesson.getId().toString())
                .set(lesson.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteLesson(Lesson lesson, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("tutors").document(lesson.getId().toString())
                .delete()
                .addOnSuccessListener(aVoid -> listener.onComplete())
                .addOnFailureListener(aVoid -> listener.onComplete());
    }

    public static void getEvents(Consumer<List<Event>> consumer){
        FirebaseFirestore.getInstance().collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    List<Event> events = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            events.add(new Event(document.getData()));
                        }
                    } else {}
                    consumer.accept(events);
                });
    }
    public static void addEvent(Event event, Model.OnCompleteListener listener){
        event.setId(eventID);
        eventID++;
        FirebaseFirestore.getInstance().collection("events").document(event.getId().toString())
                .set(event.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteEvent(Event event, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("events").document(event.getId().toString())
                .delete()
                .addOnSuccessListener(aVoid -> listener.onComplete())
                .addOnFailureListener(aVoid -> listener.onComplete());
    }
}
