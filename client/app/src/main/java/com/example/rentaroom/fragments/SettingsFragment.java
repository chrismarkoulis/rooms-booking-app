package com.example.rentaroom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentaroom.LoginActivity;
import com.example.rentaroom.R;
import com.example.rentaroom.store.StoreManager;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonEditUser).setOnClickListener(this);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
    }

    private void displayFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayoutContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.buttonLogout) {
            logout();
        } else if (id == R.id.buttonEditUser) {
            displayFragment(new EditProfileFragment());
        }
    }

    private void logout() {
        StoreManager.getInstance(getActivity()).clearStore();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
