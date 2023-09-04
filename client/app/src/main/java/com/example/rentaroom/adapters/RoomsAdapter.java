package com.example.rentaroom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentaroom.R;
import com.example.rentaroom.interfaces.RoomsRecyclerViewInterface;
import com.example.rentaroom.models.Room;
import com.example.rentaroom.models.RoomsResponse;

import java.util.List;

import retrofit2.Callback;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder> {

    private Context mCtx;
    private List<Room> roomList;

    private final RoomsRecyclerViewInterface recyclerViewInterface;

    public RoomsAdapter(Context mCtx, List<Room> roomList, RoomsRecyclerViewInterface recyclerViewInterface) {
        this.mCtx = mCtx;
        this.roomList = roomList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFilteredList(List<Room> filteredList) {
        this.roomList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_rooms, parent, false);
        return new RoomsViewHolder(view, recyclerViewInterface);
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
        if (roomList.isEmpty()){
            return 0;
        }
        return roomList.size();
    }

    static class RoomsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewLocation, textViewDescription, textViewPrice;
        public RoomsViewHolder(@NonNull View itemView, RoomsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
