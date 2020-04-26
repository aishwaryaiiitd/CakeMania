package com.example.cakemania.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cakemania.EntryActivity;
import com.example.cakemania.MainActivity;
import com.example.cakemania.R;
import com.example.cakemania.models.Cake;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CartFragment extends Fragment implements View.OnDragListener{

    ArrayList<Cake> cart_list,selected_list;
    CartListAdapter cartListAdapter;
    SelectedListAdapter selectedListAdapter;


    @BindView(R.id.cart_list_recycler_view)
    RecyclerView cart_list_recycler_view;

    @BindView(R.id.selected_list_recycler_view)
    RecyclerView selected_list_recycler_view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this,view);

        cart_list = get_cart_list();
        selected_list = get_selected_list();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        cart_list_recycler_view.setLayoutManager(layoutManager1);
        selected_list_recycler_view.setLayoutManager(layoutManager2);


        selectedListAdapter = new SelectedListAdapter(selected_list);
        cartListAdapter = new CartListAdapter(cart_list,selectedListAdapter);

        cart_list_recycler_view.setAdapter(cartListAdapter);
        selected_list_recycler_view.setAdapter(selectedListAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(selectedListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(selected_list_recycler_view);

        cart_list_recycler_view.setOnDragListener(this);
        selected_list_recycler_view.setOnDragListener(this);

        cartListAdapter.notifyDataSetChanged();
        selectedListAdapter.notifyDataSetChanged();

        return view;
    }

    private ArrayList<Cake> get_cart_list(){
        ArrayList<Cake> arrayList=new ArrayList<Cake>();
        for(Cake cake: EntryActivity.cakeArrayList){
            if(cake.isAdd_to_cart() && !cake.isSelected()){
                arrayList.add(cake);
            }
        }
        return arrayList;
    }

    private ArrayList<Cake> get_selected_list(){
        ArrayList<Cake> arrayList=new ArrayList<Cake>();
        for(Cake cake: EntryActivity.cakeArrayList){
            if(cake.isSelected()){
                arrayList.add(cake);
            }
        }
        return arrayList;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        boolean isDropped = false;
        int action = dragEvent.getAction();
        if(action==DragEvent.ACTION_DROP){

            isDropped = true;
            int positionSource = -1;
            int positionTarget = -1;

            View viewSource = (View) dragEvent.getLocalState();

            if (view.getId() == R.id.selected_list_recycler_view) {

                View onTopOf = selected_list_recycler_view.findChildViewUnder(dragEvent.getX(), dragEvent.getY());

                positionTarget = selected_list_recycler_view.getChildAdapterPosition(onTopOf);
                Log.d(TAG, "inside on drag true: "+positionTarget);

                positionSource = (int) viewSource.getTag();

                Cake cake = (Cake) cartListAdapter.getArrayList().get(positionSource);
               ArrayList<Cake> customListSource = cartListAdapter.getArrayList();

                cake.setAdd_to_cart(false);
                cake.setSelected(true);
                customListSource.remove(positionSource);
                cartListAdapter.notifyItemRemoved(positionSource);
                cartListAdapter.notifyItemRangeChanged(positionSource,customListSource.size());

                assert selectedListAdapter != null;
                ArrayList<Cake> customListTarget = selectedListAdapter.getArrayList();
                if (positionTarget >= 0) {
                    customListTarget.add(positionTarget, cake);
                    selectedListAdapter.notifyItemInserted(positionTarget);
                } else if(customListTarget.size()==0){
                    customListTarget.add(cake);
                    selectedListAdapter.notifyItemInserted(0);
                }else{
                    int ind = customListTarget.size();
                    customListTarget.add(ind,cake);
                    selectedListAdapter.notifyItemInserted(ind);
                }

                view.setVisibility(View.VISIBLE);

            }

        }

        if (!isDropped) {
            View vw = (View) dragEvent.getLocalState();
            vw.setVisibility(View.VISIBLE);
        }

        return true;
    }

}
