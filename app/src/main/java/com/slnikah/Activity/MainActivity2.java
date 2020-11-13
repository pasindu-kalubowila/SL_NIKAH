package com.slnikah.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.slnikah.Fragment.Home;
import com.slnikah.Fragment.Register;
import com.slnikah.Fragment.menu;
import com.slnikah.R;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomNavigationView = findViewById(R.id.bottom_navigation2);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.nav_register:
                    selectedFragment = new Register();
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            }

            return true;
        }


    };
}