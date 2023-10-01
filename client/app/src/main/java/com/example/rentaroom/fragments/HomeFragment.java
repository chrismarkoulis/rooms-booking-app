package com.example.rentaroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentaroom.R;
import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView textViewName, textViewEmail, textViewIsAdmin, textViewInfo;
    private Button addRoomButton;
    private List<Room> userBookings = new ArrayList<>();
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = StoreManager.getInstance(getActivity()).getUser();
        boolean isAdmin = user.isAdmin();
        String userToken = user.getToken();
        String userId = user.getId();
        Api api = RetrofitClient.makeRequest(userToken).create(Api.class);

        listView = view.findViewById(R.id.userBookings);



        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewIsAdmin = view.findViewById(R.id.textViewIsAdmin);
        textViewInfo = view.findViewById(R.id.textViewInfo);
        addRoomButton = view.findViewById(R.id.addRoomButton);

        view.findViewById(R.id.addRoomButton).setOnClickListener(this);

        Call<User> call = api.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!isAdmin) {
                    List<String> roomTitles = new ArrayList<>();
                    userBookings = response.body().getBookings();
                    for (Room r : userBookings) {
                        roomTitles.add(r.getName());
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, roomTitles);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        textViewName.setText(String.format("Welcome %s", StoreManager.getInstance(getActivity()).getUser().getName()));
        textViewEmail.setText(StoreManager.getInstance(getActivity()).getUser().getEmail());
        textViewIsAdmin.setText(isAdmin ? "Host" : "Tenant");
        textViewInfo.setText(isAdmin ? "Your Messages:" : "Your Bookings:");
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
