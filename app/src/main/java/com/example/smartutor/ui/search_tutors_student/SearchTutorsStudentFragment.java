package com.example.smartutor.ui.search_tutors_student;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartutor.R;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

public class SearchTutorsStudentFragment extends Fragment {

    private SearchTutorsStudentViewModel searchTutorsStudentViewModel;
    private RecyclerView tutorsList;
    private RadioButton searchByName;
    private RadioButton searchBySubject;
    private EditText searchTutor;
    List<String> tutorsListData;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        searchTutorsStudentViewModel =
                new ViewModelProvider(this).get(SearchTutorsStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_tutors_student, container, false);
        //final TextView textView = root.findViewById(R.id.searchTutorsStudent_title_tv);
        searchTutorsStudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        tutorsList = root.findViewById(R.id.searchTutorsStudent_listTutors_recyclerView);
        searchByName = root.findViewById(R.id.searchTutorsStudent_byName_radioBtn);
        searchBySubject = root.findViewById(R.id.searchTutorsStudent_bySubject_radioBtn);
        searchTutor = root.findViewById(R.id.searchTutorsStudent_tutor_et);
        tutorsList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        tutorsList.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        tutorsList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "Tutor " + position + " clicked");
            }
        });

        tutorsListData = new LinkedList<String>();
        for(int i = 0; i < 20; i++){
            tutorsListData.add("student " + (i+1));
        }
        return root;
    }


    static class SearchTutorsViewHolder extends RecyclerView.ViewHolder{
        OnItemClickListener listener;
        TextView nameTv;
        LinearLayout subjectsList;
        View item;

        public SearchTutorsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            item = itemView;
            nameTv = itemView.findViewById(R.id.listTutorsStudentsRow_tutorName_tv);
            subjectsList = itemView.findViewById(R.id.listTutorsStudentsRow_subjects_linearLayout);
            this.listener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onClick(position);
                        }
                    }
                }
            });
        }

        public void bind(String name, int index){
            subjectsList.removeAllViews();
            if(index%2 == 0)
            {
                ImageView image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_computer);
                subjectsList.addView(image);
            }
            ImageView image = new ImageView(itemView.getContext());
            image.setBackgroundResource(R.drawable.ic_math);
            subjectsList.addView(image);

            nameTv.setText(name);
        }
    }
    public interface OnItemClickListener {
        void onClick(int position);
    }
    class MyAdapter extends RecyclerView.Adapter<SearchTutorsViewHolder>{
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public SearchTutorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.search_tutors_student_tutors_list_row, parent, false);
            SearchTutorsViewHolder holder = new SearchTutorsViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchTutorsViewHolder holder, int position) {
            String tutor = tutorsListData.get(position);
            holder.bind(tutor, position);
        }

        @Override
        public int getItemCount() {
            return tutorsListData.size();
        }
    }
}