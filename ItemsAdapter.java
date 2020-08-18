package com.example.todoapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnClickListener
    {
        void onItemClicked(int postion);
    }

    // remome item
  public interface OnLongClickListener
  {
      void onItemLongClicked(int position);
  }

     List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    public ItemsAdapter(List<String> items , OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items=items;
        this.longClickListener=longClickListener;
        this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // creating each view useLayout inflator to inflate the view
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new ViewHolder(todoview);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       //bind the data into a position
        String item=items.get(position);
        holder.bind(item);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem=itemView.findViewById(android.R.id.text1);
        }
        public void bind(String item) {
            tvItem.setText(item);

            //communication between 2 actvties
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                   clickListener.onItemClicked(getAdapterPosition());
                }
            });
            // to remove item from the list when younlong press on button
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     //to remove items frm list... notify the listener when long presssed
                     longClickListener.onItemLongClicked(getAdapterPosition());
                     return true;
                 }
             });
        }
    }
}
