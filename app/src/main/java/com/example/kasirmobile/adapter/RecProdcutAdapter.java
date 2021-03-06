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

import java.util.List;

public class RecProdcutAdapter extends RecyclerView.Adapter<RecProdcutAdapter.ViewHolder> {

    Context context;
    List<Product> listProduct;
    DbHelper dbHelper;

    public RecProdcutAdapter(Context context, List<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_procut, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listProduct.get(position));
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSku, tvName, tvPrice, tvStock;
        View rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSku = itemView.findViewById(R.id.tv_sku_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
            tvStock = itemView.findViewById(R.id.tv_stock_product);
            rootView = itemView.findViewById(R.id.root_row_product);
        }

        void bindData(Product product) {
            tvSku.setText(product.getSku());
            tvName.setText(product.getName());
            tvPrice.setText("Rp. " + product.getPrice());
            tvStock.setText("Stock : " + product.getStock());

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertProduct(product);
                }
            });
        }

        void showAlertProduct(Product product) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            View viewAlert = LayoutInflater.from(context).inflate(R.layout.view_alert_product, null, false);

            TextView tvSku = viewAlert.findViewById(R.id.tv_sku_view_alert_prod);
            TextView tvName = viewAlert.findViewById(R.id.tv_name_view_alert_prod);
            TextView tvStock = viewAlert.findViewById(R.id.tv_stock_view_alert_prod);
            TextView tvPrice = viewAlert.findViewById(R.id.tv_price_view_alert_prod);
            NumberPicker numberPicker = viewAlert.findViewById(R.id.numpick_quantity_view_alert_prod);

            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(Integer.parseInt(product.getStock()));

            tvSku.setText(product.getSku());
            tvName.setText(product.getName());
            tvStock.setText("Stock : " + product.getStock());
            tvPrice.setText("Rp. " + product.getPrice());

            alert.setTitle("Detail Product");
            alert.setView(viewAlert);
            alert.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Integer.parseInt(product.getStock()) > 0) {
                        int stock = Integer.parseInt(product.getStock()) - numberPicker.getValue();

                        dbHelper.updateProd(new Product(
                                product.getId(),
                                product.getName(),
                                product.getSku(),
                                product.getPrice(),
                                String.valueOf(stock)
                        ));

                        dbHelper.addKeranjang(new Product(
                                product.getId(),
                                product.getName(),
                                product.getSku(),
                                product.getPrice(),
                                String.valueOf(numberPicker.getValue())
                        ));
                    } else {
                        Toast.makeText(context, "Stock Kosong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, ActivityAddSatuan.class);
                    intent.putExtra("product", product);
                    context.startActivity(intent);
                }
            });
            alert.setNeutralButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listProduct.remove(product);
                    notifyDataSetChanged();
                    dbHelper.deleteProduct(product.getId());
                    dbHelper.deleteItemKeranjang(product.getId());
                }
            });
            alert.create().show();
        }
    }
}
