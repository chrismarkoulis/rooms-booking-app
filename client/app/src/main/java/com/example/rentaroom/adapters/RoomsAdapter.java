package com.example.rentaroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaroom.R;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.store.StoreManager;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder> {

    private Context mCtx;
    private List<Room> roomList;

    public RoomsAdapter(Context mCtx, List<Room> roomList) {
        this.mCtx = mCtx;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_rooms, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.textViewName.setText(room.getName());
        holder.textViewLocation.setText(room.getLocation());
        holder.textViewDescription.setText(room.getDescription());
        holder.textViewPrice.setText(String.format("%s€", room.getPrice()));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class RoomsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewLocation, textViewDescription, textViewPrice;
        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
