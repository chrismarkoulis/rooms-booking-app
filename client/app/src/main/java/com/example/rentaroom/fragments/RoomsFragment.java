package com.example.rentaroom.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaroom.R;
import com.example.rentaroom.adapters.RoomsAdapter;
import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsFragment extends Fragment {

    private RecyclerView roomsRecyclerView;
    private RoomsAdapter roomsAdapter;
    private List<Room> roomList;
    private TextView userRoomsTitle;
    private SearchView roomsSearchView;

    User user = StoreManager.getInstance(getActivity()).getUser();
    String userId = user.getId();
    boolean isAdmin = user.isAdmin();
    Api api = RetrofitClient.makeRequest(null).create(Api.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rooms_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRoomsTitle = view.findViewById(R.id.userRoomsText);
        userRoomsTitle.setText(isAdmin ? "Your Rooms" : "Available Rooms");

        roomsRecyclerView = view.findViewById(R.id.roomRecyclerView);
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        roomsSearchView = view.findViewById(R.id.searchView);
        roomsSearchView.clearFocus();
        roomsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                filterList(text);
                return true;
            }
        });


        Call<RoomsResponse> call = api.getRooms();

        call.enqueue(new Callback<RoomsResponse>() {
            @Override
            public void onResponse(Call<RoomsResponse> call, Response<RoomsResponse> response) {
                List <Room> allRooms = response.body().getRooms();
                List <Room> hostRooms = allRooms.stream()
                                                .filter(room -> Objects.equals(room.getUserId(), userId))
                                                .collect(Collectors.toList());

                roomList = isAdmin ? hostRooms: allRooms;
                roomsAdapter = new RoomsAdapter(getActivity(), roomList);
                roomsRecyclerView.setAdapter(roomsAdapter);
            }

            @Override
            public void onFailure(Call<RoomsResponse> call, Throwable t) {

            }
        });
    }

    private void filterList(String text) {
        List<Room> filteredList = new ArrayList<>();
        for (Room room : roomList){
           String price = String.valueOf(room.getPrice());
            if (
                    room.getName().toLowerCase().contains(text.toLowerCase()) ||
                    room.getDescription().toLowerCase().contains(text.toLowerCase()) ||
                    room.getLocation().toLowerCase().contains(text.toLowerCase()) ||
                    price.contains(text.toLowerCase())
            ) {
                filteredList.add(room);
            }

            roomsAdapter.setFilteredList(filteredList);
        }
    }


}
