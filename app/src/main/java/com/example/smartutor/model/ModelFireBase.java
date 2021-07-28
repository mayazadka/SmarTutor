package com.example.smartutor.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartutor.ui.add_post.AddPostViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FieldValue;
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
        deleteLessonsByStudent(student.getEmail());
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .delete()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        if(student!=null){
                            FirebaseFirestore.getInstance()
                                    .collection("students").document(student.getEmail())
                                    .update("isDeleted", true)
                                    .addOnSuccessListener(aVoid -> {
                                        FirebaseFirestore.getInstance()
                                                .collection("students").document(student.getEmail().toString())
                                                .update("lastUpdated", FieldValue.serverTimestamp())
                                                .addOnSuccessListener(v -> listener.onComplete())
                                                .addOnFailureListener(v -> listener.onComplete());
                                    })
                                    .addOnFailureListener(aVoid -> listener.onComplete());
                        }
                        else{listener.onComplete();}
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
        deletePostsByTutor(tutor.getEmail());
        deleteEventsByTutor(tutor.getEmail());
        deleteLessonsByTutor(tutor.getEmail());
        FirebaseAuth.getInstance()
                .getCurrentUser()
                .delete()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        if(tutor!=null){
                            FirebaseFirestore.getInstance()
                                    .collection("tutors").document(tutor.getEmail())
                                    .update("isDeleted", true)
                                    .addOnSuccessListener(aVoid -> {
                                        FirebaseFirestore.getInstance()
                                                .collection("tutors").document(tutor.getEmail().toString())
                                                .update("lastUpdated", FieldValue.serverTimestamp())
                                                .addOnSuccessListener(v -> listener.onComplete())
                                                .addOnFailureListener(v -> listener.onComplete());
                                    })
                                    .addOnFailureListener(aVoid -> listener.onComplete());
                        }
                        else{listener.onComplete();}
                    }
                });
    }

    public static void getLessons(Long since, Consumer<List<Lesson>> consumer){
        FirebaseFirestore.getInstance().collection("lessons")
                .whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since, 0))
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
        if(lesson != null){
            FirebaseFirestore.getInstance()
                    .collection("lessons").document(lesson.getId().toString())
                    .update("isDeleted", true)
                    .addOnSuccessListener(aVoid -> {
                        FirebaseFirestore.getInstance()
                                .collection("lessons").document(lesson.getId().toString())
                                .update("lastUpdated", FieldValue.serverTimestamp())
                                .addOnSuccessListener(v -> listener.onComplete())
                                .addOnFailureListener(v -> listener.onComplete());
                    })
                    .addOnFailureListener(aVoid -> listener.onComplete());
        }
        else{listener.onComplete();}
    }
    public static void deleteLessonsByTutor(String email){
        FirebaseFirestore.getInstance()
            .collection("lessons")
            .whereEqualTo("tutorEmail", email)
            .get()
            .addOnCompleteListener(task->{
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        deleteLesson(new Lesson(document.getData()), ()->{});
                    }
                }

            });
    }
    public static void deleteLessonsByStudent(String email){
        FirebaseFirestore.getInstance()
                .collection("lessons")
                .whereEqualTo("studentEmail", email)
                .get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            deleteLesson(new Lesson(document.getData()), ()->{});
                        }
                    }

                });
    }

    public static void getEvents(Long since, Consumer<List<Event>> consumer){
        FirebaseFirestore.getInstance().collection("events")
                .whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since, 0))
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
        if(event != null){
            FirebaseFirestore.getInstance()
                    .collection("events").document(event.getId().toString())
                    .update("isDeleted", true)
                    .addOnSuccessListener(aVoid -> {
                        FirebaseFirestore.getInstance()
                                .collection("events").document(event.getId().toString())
                                .update("lastUpdated", FieldValue.serverTimestamp())
                                .addOnSuccessListener(v -> listener.onComplete())
                                .addOnFailureListener(v -> listener.onComplete());
                    })
                    .addOnFailureListener(aVoid -> listener.onComplete());
        }
        else{listener.onComplete();}
    }
    public static void deleteEventsByTutor(String email){
        FirebaseFirestore.getInstance()
                .collection("events")
                .whereEqualTo("tutorEmail", email)
                .get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            deleteEvent(new Event(document.getData()), ()->{});
                        }
                    }
                });
    }

    public static void getPosts(Long since, Consumer<List<Post>> consumer){
        FirebaseFirestore.getInstance().collection("posts")
                .whereGreaterThanOrEqualTo("lastUpdated", new Timestamp(since, 0))
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
    public static void addPost(Post post, Consumer<String> listener){
        FirebaseFirestore.getInstance().collection("posts")
                .add(post.toJson())
                .addOnSuccessListener(dr-> listener.accept(dr.getId()));
    }
    public static void updatePost(Post post, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection("posts").document(post.getId().toString())
                .set(post.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }

    public static void deletePostsByTutor(String email){
        FirebaseFirestore.getInstance()
                .collection("Posts")
                .whereEqualTo("tutorEmail", email)
                .get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            deletePost(new Post(document.getData()), ()->{});
                        }
                    }

                });
    }
    private static void deletePostDetails(Post post, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance()
                .collection("posts").document(post.getId().toString())
                .update("isDeleted", true)
                .addOnSuccessListener(aVoid -> {
                    FirebaseFirestore.getInstance()
                            .collection("posts").document(post.getId().toString())
                            .update("lastUpdated",FieldValue.serverTimestamp())
                            .addOnSuccessListener(v -> listener.onComplete())
                            .addOnFailureListener(v -> listener.onComplete());
                })
                .addOnFailureListener(aVoid -> listener.onComplete());
    }
    public static void deletePost(Post post, Model.OnCompleteListener listener){
        if(post.getPicture() == null || post.getPicture().equals("")){
            deletePostDetails(post, listener);
        }
        else{
            deleteImage(post.getPicture(), ()-> deletePostDetails(post, listener));
        }
    }
    public static void uploadImage(Bitmap imageBmp, String name, final Consumer<String> listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("pictures").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.accept(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.accept(downloadUrl.toString());
                    }
                });
            }
        });
    }
    public static void  deleteImage(String url, Model.OnCompleteListener listener){
        if(url != null && !url.equals(""))
        {
            FirebaseStorage.getInstance()
                    .getReferenceFromUrl(url)
                    .delete()
                    .addOnSuccessListener(aVoid -> listener.onComplete())
                    .addOnFailureListener(aVoid -> listener.onComplete());
        }
        else{
            listener.onComplete();
        }
    }

    public static void checkCurrentUser(Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            OnSuccess.onComplete();
        }
        else
            OnFailure.onComplete();
    }
    public static void createUserAccount(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        // create user with email
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener((task)->{
            if(task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("omer", "createUserWithEmail:success");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null) {
                    OnFailure.onComplete();
                }
                else {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(type)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener((task1) -> {
                                if (task1.isSuccessful()) {
                                    OnSuccess.onComplete();
                                }
                            });
                }
            } else {
                // If sign in fails, display a message to the user.
                OnFailure.onComplete();
            }
        });
    }
    public static String getCurrentUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            return user.getEmail();
        return null;
    }
    public static void signIn(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        // sign in with email
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user == null) {
                            OnFailure.onComplete();
                        }
                        else if(type.compareTo(user.getDisplayName()) == 0)
                            OnSuccess.onComplete();
                        else
                            OnFailure.onComplete();
                    } else {
                        // If sign in fails, display a message to the user.
                        OnFailure.onComplete();
                    }
                });
    }
    public static void sendEmailVerification(Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        // Send verification email
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) { OnSuccess.onComplete(); }
                    else { OnFailure.onComplete(); }
                });
    }
    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
