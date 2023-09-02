package com.example.rentaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rentaroom.R;
import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextName;
    private Spinner userRole;

    User user = StoreManager.getInstance(getActivity()).getUser();
    String userToken = user.getToken();

    Api api = RetrofitClient.makeRequest(userToken).create(Api.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        userRole = view.findViewById(R.id.roleDropdown);

        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());

        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.submitEditUser).setOnClickListener(this);
    }

    public void updateUser() {

    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            requireActivity().onBackPressed();
        } else if (id == R.id.submitEditUser) {
            updateUser();
        }
    }
}