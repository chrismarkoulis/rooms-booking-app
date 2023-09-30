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

import com.example.rentaroom.R;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;

import org.w3c.dom.Text;

public class SelectedRoomFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName, editTextLocation, editTextDescription,
                     editTextPrice, editTextOwnerName, editTextOwnerEmail;

    private TextView header;

    Room room = StoreManager.getInstance(getActivity()).getCurrentRoom();
    boolean isAdmin = StoreManager.getInstance(getActivity()).getUser().isAdmin();

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
            Log.i("ROOM BOOKED", room.toString());
        }
    }
}