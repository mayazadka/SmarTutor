package com.example.smartutor.ui.search_tutors_student;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartutor.MultiSpinner;
import com.example.smartutor.R;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.ui.tutor_details.TutorDetailsFragment;
import com.example.smartutor.ui.tutor_details.TutorDetailsFragmentDirections;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchTutorsStudentFragment extends Fragment {

    private SearchTutorsStudentViewModel searchTutorsStudentViewModel;
    private RecyclerView tutorsList;
    private MultiSpinner profesions;
    private RadioButton searchByName;
    private RadioButton searchBySubject;
    private EditText searchTutor;
    List<Tutor> tutorsListData;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchTutorsStudentViewModel = new ViewModelProvider(this).get(SearchTutorsStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_tutors_student, container, false);

        tutorsList = root.findViewById(R.id.searchTutorsStudent_listTutors_recyclerView);
        profesions = root.findViewById(R.id.searchTutorsStudent_subjects_multiSpinner);
        searchTutor = root.findViewById(R.id.searchTutorsStudent_tutor_et);
        tutorsList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        tutorsList.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        tutorsList.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            String email = tutorsListData.get(position).getEmail();
            SearchTutorsStudentFragmentDirections.ActionNavSearchTutorsStudentToNavTutorDetailsStudent action = SearchTutorsStudentFragmentDirections.actionNavSearchTutorsStudentToNavTutorDetailsStudent();
            action.setEmail(email);
            Navigation.findNavController(view).navigate(action);
        });

        tutorsListData = searchTutorsStudentViewModel.getTutors();
        searchTutor.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                tutorsListData = searchTutorsStudentViewModel.getTutorsByName(searchTutor.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                tutorsListData = searchTutorsStudentViewModel.getTutorsByName(searchTutor.getText().toString());
                tutorsList.getAdapter().notifyDataSetChanged();
            }
        });
        profesions.setItems(Arrays.asList(getResources().getStringArray(R.array.subject)), "Choose professions.", (MultiSpinner.MultiSpinnerListener) selected -> { });
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
                            listener.onClick(v, position);
                        }
                    }
                }
            });
        }

        public void bind(Tutor tutor, int index){
            subjectsList.removeAllViews();
            ImageView image;
            if (tutor.getProfessions().contains(Profession.COMPUTERSCIENCE)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_computer_science);
                subjectsList.addView(image);
            }
            if (tutor.getProfessions().contains(Profession.MATH)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_math);
                subjectsList.addView(image);
            }
            if (tutor.getProfessions().contains(Profession.HISTORY)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_history);
                subjectsList.addView(image);
            }
            if (tutor.getProfessions().contains(Profession.LANGUAGE)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_english);
                subjectsList.addView(image);
            }
            if (tutor.getProfessions().contains(Profession.LITERATURE)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_literature);
                subjectsList.addView(image);
            }
            if (tutor.getProfessions().contains(Profession.SCIENCE)) {
                image = new ImageView(itemView.getContext());
                image.setBackgroundResource(R.drawable.ic_subject_science);
                subjectsList.addView(image);
            }
            nameTv.setText(tutor.getFirstName() + " " + tutor.getLastName());
        }
    }
    public interface OnItemClickListener {
        void onClick(View view, int position);
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
            Tutor tutor = tutorsListData.get(position);
            holder.bind(tutor, position);
        }

        @Override
        public int getItemCount() {
            return tutorsListData.size();
        }
    }
}