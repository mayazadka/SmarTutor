package com.example.smartutor.ui.student_menu_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.ui.login.LogIn;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StudentMenuActivity extends AppCompatActivity {

    private StudentMenuViewModel studentMenuViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView headerTitle;
    private TextView headerSubTiitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view model
        studentMenuViewModel = new ViewModelProvider(this).get(StudentMenuViewModel.class);

        setContentView(R.layout.activity_student_menu);
        Toolbar toolbar = findViewById(R.id.appBar_student_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navViewStudent);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_student, R.id.nav_edit_details_student, R.id.nav_search_tutors_student, R.id.nav_feed_student, R.id.nav_delete_account_student, R.id.nav_tutor_details_student)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.student_navhost);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        headerTitle = navigationView.getHeaderView(0).findViewById(R.id.tutorHeader_title_tv);
        headerSubTiitle = navigationView.getHeaderView(0).findViewById(R.id.tutorHeader_subtitle_tv);

        headerTitle.setText("SmarTutor");
        headerSubTiitle.setText(getIntent().getStringExtra("EMAIL"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.tutor_menu_exit){
            studentMenuViewModel.signOut();
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.student_navhost);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}