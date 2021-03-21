package com.example.kasirmobile.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;
import com.example.kasirmobile.activity.ActivityAddSatuan;
import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.model.Product;
import com.example.kasirmobile.model.ProductHistory;

import java.util.List;

public class RecHistoryAdapter extends RecyclerView.Adapter<RecHistoryAdapter.ViewHolder> {

    Context context;
    List<ProductHistory> listProduct;
    DbHelper dbHelper;

    public RecHistoryAdapter(Context context, List<ProductHistory> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_prod_history, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listProduct.get(position));

        if (position > 0) {
            if (listProduct.get(position).getTglTransaksi().equals(listProduct.get(position-1).getTglTransaksi())) {
                holder.tvTgl.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTgl, tvSku, tvName, tvPrice, tvStock;
        View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.tv_tgl_transaksi_history);
            tvSku = itemView.findViewById(R.id.tv_sku_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
            tvStock = itemView.findViewById(R.id.tv_stock_product);
            rootView = itemView.findViewById(R.id.root_row_product);
        }

        void bindData(ProductHistory product) {
            tvTgl.setText(product.getTglTransaksi());
            tvSku.setText(product.getSku());
            tvName.setText(product.getName());
            tvPrice.setText("Rp. " + product.getPrice());
            tvStock.setText("Stock : " + product.getStock());

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
