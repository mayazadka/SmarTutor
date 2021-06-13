package com.example.smartutor.ui.search_tutors_student;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    List<Integer> tutorsListData;

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

        tutorsListData = new LinkedList<Integer>();
        for(int i = 0; i < 10; i++){
            tutorsListData.add(i);
        }
        return root;
    }


    static class SearchTutorsViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        LinearLayout subjectsList;

        public SearchTutorsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.listTutorsStudentsRow_tutorName_tv);
            subjectsList = itemView.findViewById(R.id.listTutorsStudentsRow_subjects_linearLayout);
        }

        public void bind(Integer name){
            nameTv.setText(name.toString());
        }
    }
    class MyAdapter extends RecyclerView.Adapter<SearchTutorsViewHolder>{

        @NonNull
        @Override
        public SearchTutorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.search_tutors_student_tutors_list_row, parent, false);
            SearchTutorsViewHolder holder = new SearchTutorsViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SearchTutorsViewHolder holder, int position) {
            int tutor = tutorsListData.get(position);
            holder.bind(tutor);
        }

        @Override
        public int getItemCount() {
            return tutorsListData.size();
        }
    }
}