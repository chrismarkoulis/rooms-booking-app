package com.example.rentaroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rentaroom.fragments.HomeFragment;
import com.example.rentaroom.fragments.RoomsFragment;
import com.example.rentaroom.fragments.SettingsFragment;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressWarnings("deprecation")
public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayoutContainer, fragment)
                .commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(!StoreManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.menu_rooms) {
            fragment = new RoomsFragment();
        } else if (id == R.id.menu_settings) {
            fragment = new SettingsFragment();
        }

        if(fragment != null){
            displayFragment(fragment);
        }

        return true;
    }
}