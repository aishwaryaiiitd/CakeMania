package com.example.cakemania.ui.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cakemania.R;
import com.example.cakemania.models.Cake;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedListAdapter extends RecyclerView.Adapter<SelectedListAdapter.MyViewHolder> implements ItemTouchHelperAdapter{


    private ArrayList<Cake> arrayList;

    SelectedListAdapter(ArrayList<Cake> arrayList) {
        this.arrayList = arrayList;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cake_name)
        TextView name;

        @BindView(R.id.cake_price)
        TextView price;

        @BindView(R.id.cake_weight)
        TextView weight;


        @BindView(R.id.cake_image)
        ImageView image;

        @BindView(R.id.delete_button)
        Button delete_button;

        @BindView(R.id.selected_list_view)
        LinearLayout item_layout;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    ArrayList<Cake> getArrayList() {
        return arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int   viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int  position) {

        Cake cake = arrayList.get(position);
        holder.name.setText(cake.getName());
        holder.price.setText("Rs. "+cake.getPrice());
        holder.weight.setText(cake.getWeight());

        Glide.with(holder.itemView.getContext()).load(cake.getImage_url()).fitCenter().into(holder.image);

        holder.delete_button.setOnClickListener(v -> {
            cake.setSelected(false);
            if(position != RecyclerView.NO_POSITION) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
