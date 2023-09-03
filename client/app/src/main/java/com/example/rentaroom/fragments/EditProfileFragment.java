package com.example.rentaroom.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rentaroom.LoginActivity;
import com.example.rentaroom.ProfileActivity;
import com.example.rentaroom.R;
import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;
import com.example.rentaroom.utils.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextName;
    private Spinner userRole;

    User user = StoreManager.getInstance(getActivity()).getUser();
    String userToken = user.getToken();
    String userId = user.getId();

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
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String role = userRole.getSelectedItem().toString();

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        Call<User> call = api.updateUser(userId, name, email, role.equals("Host"));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.code() == 201) {
                        if (response.body() != null) {
                            User userRes = response.body();
                            String id = userRes.getId();
                            String name = userRes.getName();
                            String email = userRes.getEmail();
                            boolean isAdmin = userRes.isAdmin();
                            User user = new User(id, name, email, isAdmin, userToken);
                            Log.d("~~ USER ~~", user.toString());
                            StoreManager.getInstance(getActivity())
                                    .saveUser(user);
                            Toast.makeText(getActivity(), String.format("Profile updated successfully"), Toast.LENGTH_LONG).show();
                            requireActivity().onBackPressed();
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String s = response.errorBody().string();
                        Toast.makeText(getActivity(), Utils.s_json(s, "message"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
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