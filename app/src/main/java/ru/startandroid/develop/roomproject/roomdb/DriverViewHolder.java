package ru.startandroid.develop.roomproject.roomdb;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.startandroid.develop.roomproject.MainActivity;
import ru.startandroid.develop.roomproject.R;

public class DriverViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    private final TextView driverId;
    private final TextView driverName;

    public DriverViewHolder(@NonNull View itemView) {
        super(itemView);
        driverId = itemView.findViewById(R.id.tvId);
        driverName = itemView.findViewById(R.id.tvName);
        itemView.setOnCreateContextMenuListener(this);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Выберите действие");
        menu.add(0, 0, 0, "Отменить регистрацию");
        menu.add(0, 1, 0, "Отменить все регистрации");
    }
}
