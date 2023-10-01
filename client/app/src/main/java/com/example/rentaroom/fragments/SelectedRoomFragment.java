package com.example.rentaroom.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentaroom.R;
import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;
import com.example.rentaroom.utils.Utils;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedRoomFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName, editTextLocation, editTextDescription,
                     editTextPrice, editTextOwnerName, editTextOwnerEmail;

    private TextView header;

    Room room = StoreManager.getInstance(getActivity()).getCurrentRoom();
    User user = StoreManager.getInstance(getActivity()).getUser();
    boolean isAdmin = user.isAdmin();

    String userToken = user.getToken();
    String userId = user.getId();
    String roomId = room.getId();
    Api api = RetrofitClient.makeRequest(userToken).create(Api.class);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextOwnerName = view.findViewById(R.id.editTextOwnerName);
        editTextOwnerEmail = view.findViewById(R.id.editTextOwnerEmail);
        header = view.findViewById(R.id.selected_room_header);

        if (!isAdmin) {
            header.setText("Book this room");
            disableInput(editTextName);
            disableInput(editTextLocation);
            disableInput(editTextDescription);
            disableInput(editTextPrice);
            disableInput(editTextOwnerName);
            disableInput(editTextOwnerEmail);
        }


        editTextName.setText(room.getName());
        editTextLocation.setText(room.getLocation());
        editTextDescription.setText(room.getDescription());
        editTextPrice.setText(String.valueOf(room.getPrice()) + "€");
        if (room.getOwner() != null) {
            editTextOwnerName.setText(room.getOwner().getName());
            editTextOwnerEmail.setText(room.getOwner().getEmail());
        }
        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.roomButton).setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_room, container, false);
    }

    void disableInput(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setBackground(null);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v,int keyCode,KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            requireActivity().onBackPressed();
        } else if (id == R.id.roomButton) {

            Call<ResponseBody> call = api.userBooking(userId, roomId);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.code() == 200) {
                            Toast.makeText(getActivity(), String.format("Your room is successfully booked"), Toast.LENGTH_LONG).show();
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
    }
}