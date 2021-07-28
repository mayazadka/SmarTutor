package com.example.smartutor.model;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ModelFireBase {

    public static void getStudents(Long since, Consumer<List<Student>> consumer){
        FirebaseFirestore.getInstance().collection(Student.STUDENTS)
                .whereGreaterThanOrEqualTo(Student.LAST_UPDATED, new Timestamp(since, 0))
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
        FirebaseFirestore.getInstance().collection(Student.STUDENTS).document(student.getEmail())
            .set(student.toJson())
            .addOnSuccessListener(v->listener.onComplete())
            .addOnFailureListener(v->listener.onComplete());
    }
    public static void updateStudent(Student student, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection(Student.STUDENTS).document(student.getEmail())
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
                                    .collection(Student.STUDENTS).document(student.getEmail())
                                    .update(Student.IS_DELETED, true)
                                    .addOnSuccessListener(aVoid -> {
                                        FirebaseFirestore.getInstance()
                                                .collection(Student.STUDENTS).document(student.getEmail().toString())
                                                .update(Student.LAST_UPDATED, FieldValue.serverTimestamp())
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
        FirebaseFirestore.getInstance().collection(Tutor.TUTORS)
                .whereGreaterThanOrEqualTo(Tutor.LAST_UPDATED, new Timestamp(since, 0))
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
        FirebaseFirestore.getInstance().collection(Tutor.TUTORS).document(tutor.getEmail())
                .set(tutor.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void updateTutor(Tutor tutor, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection(Tutor.TUTORS).document(tutor.getEmail())
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
                                    .collection(Tutor.TUTORS).document(tutor.getEmail())
                                    .update(Tutor.IS_DELETED, true)
                                    .addOnSuccessListener(aVoid -> {
                                        FirebaseFirestore.getInstance()
                                                .collection(Tutor.TUTORS).document(tutor.getEmail().toString())
                                                .update(Tutor.LAST_UPDATED, FieldValue.serverTimestamp())
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
        FirebaseFirestore.getInstance().collection(Lesson.LESSONS)
                .whereGreaterThanOrEqualTo(Lesson.LAST_UPDATED, new Timestamp(since, 0))
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
        FirebaseFirestore.getInstance().collection(Lesson.LESSONS)
                .add(lesson.toJson())
                .addOnSuccessListener(dr->{
                    lesson.setId(dr.getId());
                    updateLesson(lesson, listener);
                });
    }
    public static void updateLesson(Lesson lesson, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection(Lesson.LESSONS).document(lesson.getId().toString())
                .set(lesson.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteLesson(Lesson lesson, Model.OnCompleteListener listener){
        if(lesson != null){
            FirebaseFirestore.getInstance()
                    .collection(Lesson.LESSONS).document(lesson.getId().toString())
                    .update(Lesson.IS_DELETED, true)
                    .addOnSuccessListener(aVoid -> {
                        FirebaseFirestore.getInstance()
                                .collection(Lesson.LESSONS).document(lesson.getId().toString())
                                .update(Lesson.LAST_UPDATED, FieldValue.serverTimestamp())
                                .addOnSuccessListener(v -> listener.onComplete())
                                .addOnFailureListener(v -> listener.onComplete());
                    })
                    .addOnFailureListener(aVoid -> listener.onComplete());
        }
        else{listener.onComplete();}
    }
    public static void deleteLessonsByTutor(String email){
        FirebaseFirestore.getInstance()
            .collection(Lesson.LESSONS)
            .whereEqualTo(Lesson.TUTOR_EMAIL, email)
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
                .collection(Lesson.LESSONS)
                .whereEqualTo(Lesson.STUDENT_EMAIL, email)
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
        FirebaseFirestore.getInstance().collection(Event.EVENTS)
                .whereGreaterThanOrEqualTo(Event.LAST_UPDATED, new Timestamp(since, 0))
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
        FirebaseFirestore.getInstance().collection(Event.EVENTS)
                .add(event.toJson())
                .addOnSuccessListener(dr->{
                    event.setId(dr.getId());
                    updateEvent(event, listener);
                });
    }
    public static void updateEvent(Event event, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection(Event.EVENTS).document(event.getId().toString())
                .set(event.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void deleteEvent(Event event, Model.OnCompleteListener listener){
        if(event != null){
            FirebaseFirestore.getInstance()
                    .collection(Event.EVENTS).document(event.getId().toString())
                    .update(Event.IS_DELETED, true)
                    .addOnSuccessListener(aVoid -> {
                        FirebaseFirestore.getInstance()
                                .collection(Event.EVENTS).document(event.getId().toString())
                                .update(Event.LAST_UPDATED, FieldValue.serverTimestamp())
                                .addOnSuccessListener(v -> listener.onComplete())
                                .addOnFailureListener(v -> listener.onComplete());
                    })
                    .addOnFailureListener(aVoid -> listener.onComplete());
        }
        else{listener.onComplete();}
    }
    public static void deleteEventsByTutor(String email){
        FirebaseFirestore.getInstance()
                .collection(Event.EVENTS)
                .whereEqualTo(Event.TUTOR_EMAIL, email)
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
        FirebaseFirestore.getInstance().collection(Post.POSTS)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
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
        FirebaseFirestore.getInstance().collection(Post.POSTS)
                .add(post.toJson())
                .addOnSuccessListener(dr-> listener.accept(dr.getId()));
    }
    public static void updatePost(Post post, Model.OnCompleteListener listener){
        FirebaseFirestore.getInstance().collection(Post.POSTS).document(post.getId())
                .set(post.toJson())
                .addOnSuccessListener(v->listener.onComplete())
                .addOnFailureListener(v->listener.onComplete());
    }
    public static void updatePost(String id, String description, Model.OnCompleteListener listener) {
        FirebaseFirestore.getInstance().collection(Post.POSTS).document(id)
                .update(Post.TEXT, description)
                .addOnSuccessListener(v->{
                    FirebaseFirestore.getInstance().collection(Post.POSTS).document(id)
                            .update(Post.LAST_UPDATED, FieldValue.serverTimestamp())
                            .addOnSuccessListener(v1->listener.onComplete())
                            .addOnFailureListener(v1->listener.onComplete());
                }).addOnFailureListener(v->listener.onComplete());
    }
        public static void deletePostsByTutor(String email){
        FirebaseFirestore.getInstance()
                .collection(Post.POSTS)
                .whereEqualTo(Post.TUTOR_EMAIL, email)
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
                .collection(Post.POSTS).document(post.getId().toString())
                .update(Post.IS_DELETED, true)
                .addOnSuccessListener(aVoid -> {
                    FirebaseFirestore.getInstance()
                            .collection(Post.POSTS).document(post.getId().toString())
                            .update(Post.LAST_UPDATED,FieldValue.serverTimestamp())
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
        final StorageReference imagesRef = storage.getReference().child(Post.PICTURES_FOLDER).child(name);
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

    public static void createUserAccount(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        // create user with email
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener((task)->{
            if(task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
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
    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
