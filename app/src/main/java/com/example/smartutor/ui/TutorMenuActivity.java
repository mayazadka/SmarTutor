package com.example.smartutor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.ui.home_tutor.HomeTutorViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TutorMenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView headerTitle;
    private TextView headerSubTiitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_menu);
        Toolbar toolbar = findViewById(R.id.appBar_tutor_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navViewTutor);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_tutor, R.id.nav_edit_details_tutor, R.id.nav_my_feed_tutor, R.id.nav_delete_account_tutor)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.tutor_navhost);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        headerTitle = navigationView.getHeaderView(0).findViewById(R.id.tutorHeader_title_tv);
        headerSubTiitle = navigationView.getHeaderView(0).findViewById(R.id.tutorHeader_subtitle_tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tutor_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.tutor_navhost);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}