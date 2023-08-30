package com.example.andtdb.addEditTable1;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.example.andtdb.data.Table1;

public class Table1ListAdapter extends ListAdapter<Table1, Table1ViewHolder> {

    public Table1ListAdapter(@NonNull DiffUtil.ItemCallback<Table1> diffCallback) {
        super(diffCallback);
    }

    @Override
    public Table1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return Table1ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(Table1ViewHolder holder, int position) {
        Table1 current = getItem(position);
        holder.bind(current.col1);
    }

    static public class Table1Diff extends DiffUtil.ItemCallback<Table1> {

        @Override
        public boolean areItemsTheSame(@NonNull Table1 oldItem, @NonNull Table1 newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Table1 oldItem, @NonNull Table1 newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}