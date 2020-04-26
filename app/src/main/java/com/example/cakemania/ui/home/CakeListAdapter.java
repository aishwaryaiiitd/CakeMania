package com.example.cakemania.ui.home;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cakemania.R;
import com.example.cakemania.models.Cake;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CakeListAdapter extends RecyclerView.Adapter<CakeListAdapter.MyViewHolder>{
    private ArrayList<Cake> arrayList;

    // constructor
    public CakeListAdapter(ArrayList<Cake> arrayList) {
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

        @BindView(R.id.add_to_cart_button)
        Button atc_button;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cake_list_element,parent,false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int  position) {
        Log.v("BindViewHolder", "in onBindViewHolder");


        Cake cake = arrayList.get(position);
        holder.name.setText(cake.getName());
        holder.price.setText("Rs. "+cake.getPrice());
        holder.weight.setText(cake.getWeight());

        Glide.with(holder.itemView.getContext()).load(cake.getImage_url()).fitCenter().into(holder.image);

        if(cake.isAdd_to_cart() || cake.isSelected()){
            holder.atc_button.setText(R.string.added_to_cart);
            holder.atc_button.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        }

        holder.atc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(cake.isAdd_to_cart() || cake.isSelected())){
                    cake.setAdd_to_cart(true);
                    holder.atc_button.setText(R.string.added_to_cart);
                    holder.atc_button.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.green));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
