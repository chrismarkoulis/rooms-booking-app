package com.example.rentaroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentaroom.R;
import com.example.rentaroom.store.StoreManager;

public class HomeFragment extends Fragment {

    private TextView textViewName, textViewEmail, textViewIsAdmin, textViewInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isAdmin = StoreManager.getInstance(getActivity()).getUser().isAdmin();

        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewIsAdmin = view.findViewById(R.id.textViewIsAdmin);
        textViewInfo = view.findViewById(R.id.textViewInfo);

        textViewName.setText(String.format("Welcome %s", StoreManager.getInstance(getActivity()).getUser().getName()));
        textViewEmail.setText(StoreManager.getInstance(getActivity()).getUser().getEmail());
        textViewIsAdmin.setText(isAdmin ? "Roomer" : "Guest");
        textViewInfo.setText(isAdmin ? "You have no rooms yet." : "Feel free to browse our available rooms!");
    }
}
