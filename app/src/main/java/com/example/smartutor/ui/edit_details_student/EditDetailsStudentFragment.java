package com.example.smartutor.ui.edit_details_student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDetailsStudentFragment extends Fragment {

    private EditDetailsStudentViewModel editDetailsStudentViewModel;
    private EditText lastName;
    private EditText firstName;
    private Spinner gender;
    private Spinner grade;
    private Button chooseDate;
    private EditText date;
    private Button save;
    private SwipeRefreshLayout swipeUp;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsStudentViewModel = new ViewModelProvider(this).get(EditDetailsStudentViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_student, container, false);

        // views setup
        lastName = root.findViewById(R.id.editDetailsStudent_lastName_et);
        firstName = root.findViewById(R.id.editDetailsStudent_firstName_et);
        gender = root.findViewById(R.id.editDetailsStudent_gender_spn);
        grade = root.findViewById(R.id.editDetailsStudent_grade_spn);
        chooseDate = root.findViewById(R.id.editDetailsStudent_birthdayDate_btn);
        date = root.findViewById(R.id.editDetailsStudent_birthdayDate_et);
        save = root.findViewById(R.id.editDetailsStudent_save_btn);
        swipeUp = root.findViewById(R.id.editDetailsStudent_swipeUp);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.grade, R.layout.spinner_item);
        grade.setAdapter(gradeAdapter);

        // events setup
        chooseDate.setOnClickListener(v -> {
            DatePicker picker = new DatePicker(getContext());
            picker.setCalendarViewShown(false);
            picker.setMaxDate(new Date().getTime());
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogStyle)
                    .setView(picker)
                    .setPositiveButton("OK", (dialog, which) -> dialog.cancel())
                    .setOnCancelListener(dialog -> date.setText(picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear()));
            builder.show();
        });

        editDetailsStudentViewModel.getStudent().observe(getViewLifecycleOwner(), student -> {
            if(student!=null){
                lastName.setText(student.getLastName());
                firstName.setText(student.getFirstName());
                switch (student.getGender()){
                    case MALE:
                        gender.setSelection(0);
                        break;
                    case FEMALE:
                        gender.setSelection(1);
                        break;
                    case OTHER:
                        gender.setSelection(2);
                        break;
                }
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);
                date.setText(dateFormat.format(student.getBirthdayDate()));
                grade.setSelection(student.getGrade() - 1);
            }
        });


        save.setOnClickListener(v -> {
            v.setEnabled(false);
            try {
                Utilities.validateLastName(lastName.getText().toString());
                Utilities.validateFirstName(firstName.getText().toString());
                Utilities.validateDate(date.getText().toString());

                Student student = new Student(null, lastName.getText().toString(), firstName.getText().toString(), Gender.valueOf(gender.getSelectedItem().toString().toUpperCase()), Utilities.convertToDate(date.getText().toString()), Utilities.convertToGrade(grade.getSelectedItem().toString()));
                editDetailsStudentViewModel.updateStudent(student, ()->{});
                Navigation.findNavController(root).navigate(R.id.action_global_nav_home_student);
            }
            catch (Exception e) {
                Snackbar.make(save, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                v.setEnabled(true);
            }

        });

        swipeUp.setOnRefreshListener(()-> editDetailsStudentViewModel.refresh());
        editDetailsStudentViewModel.getStudentLoadingState().observe(getViewLifecycleOwner(), state -> {
            boolean b = (state == LoadingState.loaded);
            save.setEnabled(b);
            swipeUp.setRefreshing(!b);
        });
        return root;
    }
}