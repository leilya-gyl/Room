package ru.startandroid.develop.roomproject.roomdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.startandroid.develop.roomproject.R;

public class DriverViewHolder extends RecyclerView.ViewHolder {

    private final TextView driverId;
    private final TextView driverName;

    public DriverViewHolder(@NonNull View itemView) {
        super(itemView);
        driverId = itemView.findViewById(R.id.tvId);
        driverName = itemView.findViewById(R.id.tvName);
    }

    public void bind(long id, String text) {
        driverId.setText(Long.toString(id));
        driverName.setText(text);
    }

    static DriverViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DriverViewHolder(view);
    }
}
