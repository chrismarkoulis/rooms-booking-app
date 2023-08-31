package com.example.rentaroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaroom.R;
import com.example.rentaroom.adapters.RoomsAdapter;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomsFragment extends Fragment {

    private RecyclerView roomsRecyclerView;
    private RoomsAdapter roomsAdapter;
    private List<Room> roomList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rooms_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        roomsRecyclerView = view.findViewById(R.id.roomRecyclerView);
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<RoomsResponse> call = RetrofitClient.getInstance().getApi().getRooms();

        call.enqueue(new Callback<RoomsResponse>() {
            @Override
            public void onResponse(Call<RoomsResponse> call, Response<RoomsResponse> response) {
                roomList = response.body().getRooms();
                roomsAdapter = new RoomsAdapter(getActivity(), roomList);
                roomsRecyclerView.setAdapter(roomsAdapter);
            }

            @Override
            public void onFailure(Call<RoomsResponse> call, Throwable t) {

            }
        });
    }
}
