package com.example.smartutor.ui.edit_details_tutor;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartutor.MultiSpinner;
import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Tutor;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsTutorFragment extends Fragment {

    private EditDetailsTutorViewModel editDetailsTutorViewModel;
    //views
    private EditText lastName;
    private EditText firstName;
    private Spinner gender;
    private EditText date;
    private Button chooseDate;
    private MultiSpinner professions;
    private EditText aboutMe;
    private Button save;
    private SwipeRefreshLayout swipeUp;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsTutorViewModel = new ViewModelProvider(this).get(EditDetailsTutorViewModel.class);
        editDetailsTutorViewModel.initial();

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_tutor, container, false);

        // views setup
        lastName = root.findViewById(R.id.editDetailsTutor_lastName_et);
        firstName = root.findViewById(R.id.editDetailsTutor_firstName_et);
        gender = root.findViewById(R.id.editDetailsTutor_gender_spn);
        date = root.findViewById(R.id.editDetailsTutor_birthdayDate_et);
        chooseDate = root.findViewById(R.id.editDetailsTutor_birthdayDate_btn);
        professions = root.findViewById(R.id.editDetailsTutor_professions_spn);
        aboutMe = root.findViewById(R.id.signUpTutor_aboutMe_etml);
        save = root.findViewById(R.id.editDetailsTutor_save_btn);
        swipeUp = root.findViewById(R.id.editDetailsTutor_swipeUp);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);

        professions.setItems(Arrays.asList(getResources().getStringArray(R.array.subject)), "Choose professions.", (MultiSpinner.MultiSpinnerListener) selected -> { });

        // events setup
        chooseDate.setOnClickListener(v -> {
            DatePicker picker = new DatePicker(getContext());
            picker.setCalendarViewShown(false);
            picker.setMaxDate(new Date().getTime());
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogStyle)
                    .setView(picker)
                    .setPositiveButton("OK", (dialog, which) -> dialog.cancel())
                    .setOnCancelListener(dialog -> date.setText(picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear()));
            builder.show();
        });

        editDetailsTutorViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> {
            if(tutor != null){
                lastName.setText(tutor.getLastName());
                firstName.setText(tutor.getFirstName());
                switch (tutor.getGender()){
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
                date.setText(dateFormat.format(tutor.getBirthdayDate()));
                Profession[] allProfessions = Profession.values();
                boolean selected[] = new boolean[allProfessions.length];
                for (int i=0;i<selected.length;i++){
                    selected[i] = tutor.getProfessions().indexOf(allProfessions[i]) != -1;
                }
                professions.setSelected(selected);
                aboutMe.setText(tutor.getAboutMe());
            }
        });

        save.setOnClickListener(v -> {
            v.setEnabled(false);
            try{
                Utilities.validateLastName(lastName.getText().toString());
                Utilities.validateFirstName(firstName.getText().toString());
                Utilities.validateDate(date.getText().toString());
                Utilities.validateProfessions(professions.getSelectedItem());
                Utilities.validateAboutMe(aboutMe.getText().toString());

                Tutor tutor = new Tutor(null, lastName.getText().toString(), firstName.getText().toString(), Gender.valueOf(gender.getSelectedItem().toString().toUpperCase()), Utilities.convertToDate(date.getText().toString()), Utilities.convertToProfessions(professions.getSelectedItem()), aboutMe.getText().toString());
                editDetailsTutorViewModel.updateTutor(tutor, ()-> Navigation.findNavController(root).navigate(R.id.action_global_nav_home_tutor));
            }
            catch (Exception e){
                Snackbar.make(save, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        swipeUp.setOnRefreshListener(()-> Model.getInstance().refreshTutors());
        Model.getInstance().tutorLoadingState.observe(getViewLifecycleOwner(), state -> {
            if(state == Model.LoadingState.loaded){
                save.setEnabled(true);
                swipeUp.setRefreshing(false);
            }
            else{
                save.setEnabled(false);
                swipeUp.setRefreshing(true);
            }
        });

        return root;
    }
}