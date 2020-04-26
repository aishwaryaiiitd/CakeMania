package com.example.cakemania.ui.cart;

import android.content.ClipData;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>{



    private ArrayList<Cake> arrayList;
    private SelectedListAdapter selectedListAdapter;

    ArrayList<Cake> getArrayList() {
        return arrayList;
    }

    CartListAdapter(ArrayList<Cake> arrayList, SelectedListAdapter selectedListAdapter) {
        this.selectedListAdapter=selectedListAdapter;
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

        @BindView(R.id.move_button)
        Button move_button;

        @BindView(R.id.delete_button)
        Button delete_button;

        @BindView(R.id.add_to_cart_list_item_view)
        LinearLayout layout_view;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int   viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_to_cart_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int  position) {

        Cake cake = arrayList.get(position);
        holder.name.setText(cake.getName());
        holder.price.setText("Rs. "+cake.getPrice());
        holder.weight.setText(cake.getWeight());
        Glide.with(holder.itemView.getContext()).load(cake.getImage_url()).fitCenter().into(holder.image);

        holder.delete_button.setOnClickListener(v -> {
            cake.setAdd_to_cart(false);
            if(position != RecyclerView.NO_POSITION) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
        });

        holder.layout_view.setOnLongClickListener(view -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            view.setTag(position);
            return true;
        });


        holder.move_button.setOnClickListener(v -> {
            cake.setSelected(true);
            cake.setAdd_to_cart(false);
            if(position != RecyclerView.NO_POSITION) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
            selectedListAdapter.getArrayList().add(0, cake);
            selectedListAdapter.notifyItemInserted(0);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
