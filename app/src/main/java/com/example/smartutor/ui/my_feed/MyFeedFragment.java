package com.example.smartutor.ui.my_feed;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Post;
import com.example.smartutor.ui.edit_post.EditPostFragmentDirections;
import com.example.smartutor.ui.tutor_details.TutorDetailsFragmentArgs;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class MyFeedFragment extends Fragment {
    private MyFeedViewModel myFeedViewModel;
    private List<Post> listPosts;
    private String tutorEmail;

    private RecyclerView postListRecyclerView;
    private Button add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tutorEmail = getActivity().getIntent().getStringExtra("EMAIL");

        myFeedViewModel = new ViewModelProvider(this).get(MyFeedViewModel.class);
        myFeedViewModel.initial(tutorEmail);

        View view = inflater.inflate(R.layout.fragment_my_feed, container, false);

        postListRecyclerView = view.findViewById(R.id.myFeed_list_rv);
        add = view.findViewById(R.id.myFeed_addPost_btn);
        postListRecyclerView.setHasFixedSize(true);

        postListRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        postListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        MyFeedFragment.MyAdapter adapter = new MyFeedFragment.MyAdapter();

        adapter.setOnItemClickListener((v, p) ->{
            Log.d("TAG", "id: " + listPosts.get(p).getId() + " " + listPosts.get(p).getText());
            MyFeedFragmentDirections.ActionNavMyFeedTutorToEditPostFragment action = MyFeedFragmentDirections.actionNavMyFeedTutorToEditPostFragment(listPosts.get(p).getId());
            Navigation.findNavController(view).navigate(action);
        });
        postListRecyclerView.setAdapter(adapter);
        add.setOnClickListener(v->Navigation.findNavController(view).navigate(R.id.action_nav_my_feed_tutor_to_addPostFragment));

        listPosts = new LinkedList<>();

        myFeedViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            if(posts == null){posts = new LinkedList<>();}

            listPosts = posts.stream().filter(post->post.getTutorEmail().equals(tutorEmail)).collect(Collectors.toList());
            postListRecyclerView.getAdapter().notifyDataSetChanged();
        });

        return view;
    }


    static class MyFeedViewHolder extends RecyclerView.ViewHolder{
        MyFeedFragment.OnItemClickListener listener;
        TextView owner;
        ImageView postImg;
        TextView description;
        View item;

        public MyFeedViewHolder(@NonNull View itemView, MyFeedFragment.OnItemClickListener listener) {
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
            if(image != null && !image.equals("")){
                Picasso.get().load(image).placeholder(R.drawable.ic_baseline_hourglass_empty_24).error(R.drawable.ic_baseline_report_problem_24).into(this.postImg);
            }
        }
    }
    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyFeedFragment.MyFeedViewHolder>{
        MyFeedFragment.OnItemClickListener listener;

        public void setOnItemClickListener(MyFeedFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyFeedFragment.MyFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.post_row, parent, false);
            MyFeedFragment.MyFeedViewHolder holder = new MyFeedFragment.MyFeedViewHolder(view, listener);
            return holder;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(@NonNull MyFeedFragment.MyFeedViewHolder holder, int position) {
            Post post = listPosts.get(position);
            myFeedViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> {
                if(tutor!=null){
                    holder.bind(tutor.getFirstName() + " " + tutor.getLastName(), post.getPicture(), post.getText());
                }
            });
        }

        @Override
        public int getItemCount() {
            return listPosts.size();
        }
    }

}