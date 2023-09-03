package com.example.rentaroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentaroom.R;
import com.example.rentaroom.store.StoreManager;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView textViewName, textViewEmail, textViewIsAdmin, textViewInfo;
    private Button addRoomButton;

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
        addRoomButton = view.findViewById(R.id.addRoomButton);

        view.findViewById(R.id.addRoomButton).setOnClickListener(this);

        textViewName.setText(String.format("Welcome %s", StoreManager.getInstance(getActivity()).getUser().getName()));
        textViewEmail.setText(StoreManager.getInstance(getActivity()).getUser().getEmail());
        textViewIsAdmin.setText(isAdmin ? "Host" : "Tenant");
        textViewInfo.setText(isAdmin ? "You have no rooms yet." : "Feel free to browse our available rooms!");
        addRoomButton.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);
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

       if (id == R.id.addRoomButton) {
           displayFragment(new AddRoomFragment());
       }

    }
}
