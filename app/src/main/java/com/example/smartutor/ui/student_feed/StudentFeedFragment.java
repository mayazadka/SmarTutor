package com.example.smartutor.ui.student_feed;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentFeedFragment extends Fragment {

    private StudentFeedViewModel studentFeedViewModel;
    private List<Post> listPosts;
    private SwipeRefreshLayout swipeUp;
    private AtomicBoolean enabled;

    //views
    private RecyclerView postListRecyclerView;

    public StudentFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studentFeedViewModel = new ViewModelProvider(this).get(StudentFeedViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_feed, container, false);

        postListRecyclerView = view.findViewById(R.id.studentFeed_list_rv);
        swipeUp = view.findViewById(R.id.studentFeed_swipeUp);

        postListRecyclerView.setHasFixedSize(true);
        postListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        StudentFeedFragment.MyAdapter adapter = new StudentFeedFragment.MyAdapter();

        postListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        enabled = new AtomicBoolean();
        enabled.set(true);
        adapter.setOnItemClickListener((v, p) ->{
            if(enabled.get()){
                enabled.set(false);
                String tutorEmail = listPosts.get(p).getTutorEmail();
                StudentFeedFragmentDirections.ActionNavFeedStudentToNavTutorDetailsStudent action = StudentFeedFragmentDirections.actionNavFeedStudentToNavTutorDetailsStudent(tutorEmail);
                Navigation.findNavController(view).navigate(action);

            }
        });
        postListRecyclerView.setAdapter(adapter);
        listPosts = new LinkedList<>();
        studentFeedViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            if(posts == null){posts = new LinkedList<>();}
            listPosts = posts;
            postListRecyclerView.getAdapter().notifyDataSetChanged();
        });

        swipeUp.setOnRefreshListener(()-> studentFeedViewModel.refresh());
        studentFeedViewModel.getTutorLoadingState().observe(getViewLifecycleOwner(), state->handleLoading());
        studentFeedViewModel.getPostLoadingState().observe(getViewLifecycleOwner(), state->handleLoading());


        return view;
    }
    private void handleLoading(){
        boolean b = studentFeedViewModel.getTutorLoadingState().getValue() == LoadingState.loaded &&
                    studentFeedViewModel.getPostLoadingState().getValue() == LoadingState.loaded;
        enabled.set(b);
        swipeUp.setRefreshing(!b);
    }

    private static class StudentFeedViewHolder extends RecyclerView.ViewHolder{
        StudentFeedFragment.OnItemClickListener listener;
        TextView owner;
        ImageView postImg;
        TextView description;
        View item;

        public StudentFeedViewHolder(@NonNull View itemView, StudentFeedFragment.OnItemClickListener listener) {
            super(itemView);
            item = itemView;
            owner = itemView.findViewById(R.id.postRow_owner_tv);
            postImg = itemView.findViewById(R.id.postRow_post_img);
            description = itemView.findViewById(R.id.postRow_description_tv);
            this.listener = listener;
            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onClick(v, position);
                    }
                }
            });
        }

        public void bind(String owner, String image, String description){
            this.owner.setText(owner);
            this.description.setText(description);
            this.postImg.setImageResource(R.drawable.ic_baseline_hourglass_empty_24);
            if(image != null && image != ""){
                Picasso.get().load(image).placeholder(R.drawable.ic_baseline_hourglass_empty_24).error(R.drawable.ic_baseline_report_problem_24).into(this.postImg);
            }
        }
    }
    private interface OnItemClickListener {
        void onClick(View view, int position);
    }
    private class MyAdapter extends RecyclerView.Adapter<StudentFeedFragment.StudentFeedViewHolder>{
        StudentFeedFragment.OnItemClickListener listener;

        public void setOnItemClickListener(StudentFeedFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public StudentFeedFragment.StudentFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.post_row, parent, false);
            StudentFeedFragment.StudentFeedViewHolder holder = new StudentFeedFragment.StudentFeedViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull StudentFeedFragment.StudentFeedViewHolder holder, int position) {
            Post post = listPosts.get(position);
            studentFeedViewModel.getTutor(post.getTutorEmail()).observe(getViewLifecycleOwner(), tutor->{
                holder.bind(tutor.getFirstName() + " " + tutor.getLastName(), post.getPicture(), post.getText());
            });
        }

        @Override
        public int getItemCount() {
            return listPosts.size();
        }
    }

}