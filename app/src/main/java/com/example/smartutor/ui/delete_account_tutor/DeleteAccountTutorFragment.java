package com.example.smartutor.ui.delete_account_tutor;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartutor.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeleteAccountTutorFragment extends Fragment {

    private DeleteAccountTutorViewModel deleteAccountTutorViewModel;

    private Button yes;
    private Button no;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        deleteAccountTutorViewModel = new ViewModelProvider(this).get(DeleteAccountTutorViewModel.class);
        deleteAccountTutorViewModel.initial();
        View root = inflater.inflate(R.layout.fragment_delete_account_tutor, container, false);

        yes = root.findViewById(R.id.deleteAccountTutor_yes_btn);
        no = root.findViewById(R.id.deleteAccountTutor_no_btn);

        yes.setOnClickListener(v -> {
            v.setEnabled(false);
            deleteAccountTutorViewModel.deleteTutor(()->getActivity().finish());
        });

        no.setOnClickListener(v -> Navigation.findNavController(root).navigate(R.id.action_global_nav_home_tutor));

        return root;
    }
}