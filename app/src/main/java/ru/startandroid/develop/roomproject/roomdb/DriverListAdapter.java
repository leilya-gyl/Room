package ru.startandroid.develop.roomproject.roomdb;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class DriverListAdapter extends ListAdapter<Driver, DriverViewHolder> {

    private int position;

    public int getPosition()    {   return position;    }

    public void setPosition(int position)   {   this.position = position;  }

    public DriverListAdapter(@NonNull DiffUtil.ItemCallback<Driver> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DriverViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Driver current = getItem(position);
        holder.bind(current.get_id(), current.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull DriverViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public static class DriverDiff extends DiffUtil.ItemCallback<Driver> {

        @Override
        public boolean areItemsTheSame(@NonNull Driver oldItem, @NonNull Driver newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Driver oldItem, @NonNull Driver newItem) {
            return oldItem.get_id() == newItem.get_id();
        }
    }
}
