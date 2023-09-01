package com.example.rentaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentaroom.LoginActivity;
import com.example.rentaroom.MainActivity;
import com.example.rentaroom.R;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.utils.Utils;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoomFragment extends Fragment implements View.OnClickListener {

    private TextView editTextName, editTextLocation, editTextDescription, editTextPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextPrice = view.findViewById(R.id.editTextPrice);

        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.addRoomButton).setOnClickListener(this);
    }

    private void createRoom () {
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String priceValue = editTextPrice.getText().toString().trim();
        int price = 0;

        try {
            price = Integer.parseInt(editTextPrice.getText().toString().trim());;
        } catch(NumberFormatException ex) {
            // handle your exception
        }

        if (name.isEmpty()) {
            editTextName.setError("Title is required");
            editTextName.requestFocus();
            return;
        }

        if (location.isEmpty()) {
            editTextLocation.setError("Room Location required");
            editTextLocation.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            editTextDescription.setError("Description is required");
            editTextDescription.requestFocus();
            return;
        }

        if (priceValue.isEmpty()) {
            editTextLocation.setError("Price is required");
            editTextLocation.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createRoom(name, location, description, price);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 201) {
                        Toast.makeText(getActivity(), String.format("Your room at %s is ready", location), Toast.LENGTH_LONG).show();
                        requireActivity().onBackPressed();
                    } else {
                        String s = response.errorBody().string();
                        Toast.makeText(getActivity(), Utils.s_json(s, "message"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            requireActivity().onBackPressed();
        } else if (id == R.id.addRoomButton) {
            createRoom();
        }
    }
}