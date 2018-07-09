package edu.qc.seclass.glm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder> {
    List<GroceryList> groceryLists;
    GroceryListClickListener listener;

    public GroceryListAdapter(List<GroceryList> groceryLists, GroceryListClickListener listener) {
        if(groceryLists == null) { throw new IllegalArgumentException("GroceryList is null.");}
        if(listener == null) { throw new IllegalArgumentException("listener is null.");}

        this.groceryLists = groceryLists;
        this.listener = listener;
    }

    @Override
    public GroceryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_grocery_list, parent, false);
        return new GroceryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroceryListViewHolder holder, int position) {
        holder.setup(groceryLists.get(position));
    }

    public int getItemCount() {
        return groceryLists.size();
    }

    class GroceryListViewHolder extends RecyclerView.ViewHolder {
        public TextView groceryListName;

        public GroceryListViewHolder(View itemView) {
            super(itemView);
            groceryListName = itemView.findViewById(R.id.text_grocerylist_name);
            CheckBox checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.groceryListClicked(getAdapterPosition());
                }
            });
        }

        public void setup(GroceryList currentGroceryList) {
            groceryListName.setText(currentGroceryList.getName());
        }
    }

    public interface GroceryListClickListener {
        void groceryListClicked(int position);
    }
}
