package edu.qc.seclass.glm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    private List<String> productList;
    private ProducListClickListener listener;

    public ProductListAdapter(List<String> products, ProducListClickListener listener) {
        if(products == null) { throw new NullPointerException("Products cannot be null."); }
        this.productList = products;
        this.listener = listener;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_product, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        holder.setUp(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {
        TextView productNameText;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            productNameText = itemView.findViewById(R.id.text_product_name);
            productNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.productClicked(productNameText.getText().toString());
                }
            });
        }

        public void setUp(final String currentItem) {
            productNameText.setText(currentItem);
        }
    }

    interface ProducListClickListener {
        void productClicked(String item);
    }
}

