package com.example.smartutor.ui.my_feed;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.LinkedList;
import java.util.List;


public class MyFeedFragment extends Fragment {
    private RecyclerView postList;
    private List<Post> posts;
    private Button add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_feed, container, false);

        postList = view.findViewById(R.id.myFeed_list_rv);
        add = view.findViewById(R.id.myFeed_addPost_btn);

        posts = new LinkedList<>();
        posts.add(new Post(1,"" , "hi", "1"));
        posts.add(new Post(1, "", "bye", "1"));
        posts.add(new Post(1, "", "what?", "1"));
        posts.add(new Post(1, "", "really!!", "1"));
        posts.add(new Post(1, "", "OMG", "1"));

        postList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        MyFeedFragment.MyAdapter adapter = new MyFeedFragment.MyAdapter();
        adapter.setOnItemClickListener((v, p) ->{
            Navigation.findNavController(view).navigate(R.id.action_nav_my_feed_tutor_to_editPostFragment);
        });
        postList.setAdapter(adapter);
        add.setOnClickListener(v->Navigation.findNavController(view).navigate(R.id.action_nav_my_feed_tutor_to_addPostFragment));



        return view;
    }


    static class MyFeedViewHolder extends RecyclerView.ViewHolder{
        MyFeedFragment.OnItemClickListener listener;
        TextView owner;
        ImageView post;
        TextView description;
        View item;

        public MyFeedViewHolder(@NonNull View itemView, MyFeedFragment.OnItemClickListener listener) {
            super(itemView);
            item = itemView;
            owner = itemView.findViewById(R.id.postRow_owner_tv);
            post = itemView.findViewById(R.id.postRow_post_img);
            description = itemView.findViewById(R.id.postRow_descriptionn_tv);
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
            this.post.setImageResource(R.drawable.ic_gender_male);
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
            Post post = posts.get(position);
            //Tutor tutor = Model.getInstance().getTutor("omer5144@gmail.com").getTutor();
            holder.bind("a", null, new Integer(position).toString());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

}