package com.example.kasirmobile.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;

import java.util.List;

public class RecProdcutAdapter extends RecyclerView.Adapter<RecProdcutAdapter.ViewHolder> {

    Context context;
    List<String> listProduct;

    public RecProdcutAdapter(Context context, List<String> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_procut, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(listProduct.get(position));
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSku, tvName, tvPrice, tvStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSku = itemView.findViewById(R.id.tv_sku_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
            tvStock = itemView.findViewById(R.id.tv_stock_product);
        }
    }
}
