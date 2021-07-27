package com.example.smartutor.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartutor.ui.add_post.AddPostViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ModelFireBase {
    private static Long lessonID = Long.valueOf(0);
    private static Long eventID = Long.valueOf(0);
    private static Long postID = Long.valueOf(0);

    //********auth************
    private static FirebaseAuth mAuth;

    public ModelFireBase(){
        //********auth************
        mAuth = FirebaseAuth.getInstance();

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
        FirebaseFirestore.getInstance().collection("posts")
                .orderBy("id", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            postID = (Long)document.get("id")+1;
                        }
                    } else {}
                });
    }

    public static void getStudents(Long since, Consumer<List<Student>> consumer){
        FirebaseFirestore.getInstance().collection("students")
                .whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since, 0))
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
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .delete()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        FirebaseFirestore.getInstance()
                                .collection("students").document(student.getEmail())
                                .update("isDeleted", true)
                                .addOnSuccessListener(aVoid -> listener.onComplete())
                                .addOnFailureListener(aVoid -> listener.onComplete());
                    }
                });
    }

    public static void getTutors(Long since, Consumer<List<Tutor>> consumer){
        FirebaseFirestore.getInstance().collection("tutors")
                .whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since, 0))
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
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .delete()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        FirebaseFirestore.getInstance()
                                .collection("tutors").document(tutor.getEmail())
                                .update("isDeleted", true)
                                .addOnSuccessListener(aVoid -> listener.onComplete())
                                .addOnFailureListener(aVoid -> listener.onComplete());
                    }
                });
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
                .collection("lessons").document(lesson.getId().toString())
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

    public static void getPosts(Consumer<List<Post>> consumer){
        FirebaseFirestore.getInstance().collection("posts")
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> posts = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            posts.add(new Post(document.getData()));
                        }
                    } else {}
                    consumer.accept(posts);
                });
    }
    public static Long addPost(Post post, Model.OnCompleteListener listener){
        post.setId(postID);
        postID++;
        FirebaseFirestore.getInstance().collection("posts").document(post.getId().toString())
                .set(post.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
        return post.getId();
    }
    public static void updatePost(Long postId, Post post, Model.OnCompleteListener listener){
        post.setId(postId);
        FirebaseFirestore.getInstance().collection("posts").document(postId.toString())
                .set(post.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deletePost(Post post, Model.OnCompleteListener listener){
        if(post.getPicture().compareTo("") == 0 || post.getPicture() == null){
            deletePostDetails(post, listener);
        }
        else{
            FirebaseStorage.getInstance()
                    .getReferenceFromUrl(post.getPicture())
                    .delete()
                    .addOnSuccessListener(aVoid -> deletePostDetails(post, listener))
                    .addOnFailureListener(aVoid -> deletePostDetails(post, listener));
        }
    }
    private static void deletePostDetails(Post post, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("posts").document(post.getId().toString())
                .delete()
                .addOnSuccessListener(aVoid -> listener.onComplete())
                .addOnFailureListener(aVoid -> listener.onComplete());
    }

    public static void uploadImage(Bitmap imageBmp, String name, final AddPostViewModel.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("pictures").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

    public static boolean checkCurrentUser(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return true;
        }
        else
            return false;
    }
    public static boolean createUserAccount(String email, String password) {
        // create user with email
        AtomicBoolean result = new AtomicBoolean(false);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task)->{
            if(task.isSuccessful()){
                // Sign in success, update UI with the signed-in user's information
                Log.d("omer", "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                result.set(true);
                //updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                Log.d("omer", "createUserWithEmail:failure", task.getException());
                /*Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();*/
                result.set(false);
                //updateUI(null);
            }
        });
        return result.get();
    }
    public static void signIn(String email, String password, Model.OnCompleteSignInListener listener) {
        // sign in with email
        AtomicBoolean result = new AtomicBoolean(false);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        listener.onComplete(true);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG", "signInWithEmail:failure", task.getException());
                        //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                        listener.onComplete(false);
                    }
                });
    }
    public static boolean sendEmailVerification() {
        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((task) -> {
                    // Email sent
                });
        return true;
    }
}
