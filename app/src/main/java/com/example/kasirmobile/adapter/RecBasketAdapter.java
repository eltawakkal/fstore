package com.example.kasirmobile.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kasirmobile.R;
import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.model.Product;
import java.util.List;

public class RecBasketAdapter extends RecyclerView.Adapter<RecBasketAdapter.ViewHolder> {

    Context context;
    List<Product> listProduct;
    DbHelper dbHelper;

    public RecBasketAdapter(Context context, List<Product> listProduct) {
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
            Product stockProduct = dbHelper.getProduct(product.getId());

            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            View viewAlert = LayoutInflater.from(context).inflate(R.layout.view_alert_product, null, false);

            TextView tvSku = viewAlert.findViewById(R.id.tv_sku_view_alert_prod);
            TextView tvName = viewAlert.findViewById(R.id.tv_name_view_alert_prod);
            TextView tvStock = viewAlert.findViewById(R.id.tv_stock_view_alert_prod);
            TextView tvPrice = viewAlert.findViewById(R.id.tv_price_view_alert_prod);
            NumberPicker numberPicker = viewAlert.findViewById(R.id.numpick_quantity_view_alert_prod);

            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(Integer.parseInt(product.getStock()) + Integer.parseInt(stockProduct.getStock()));

            tvSku.setText(product.getSku());
            tvName.setText(product.getName());
            tvStock.setText("Stock : " + product.getStock());
            tvPrice.setText("Rp. " + product.getPrice());

            alert.setCancelable(false);
            alert.setTitle("Detail Product");
            alert.setView(viewAlert);
            alert.setPositiveButton("Perbarui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int stock = 0;

                    stock = (Integer.parseInt(stockProduct.getStock()) + Integer.parseInt(product.getStock())) - numberPicker.getValue();
                    Toast.makeText(context, "Size : " + stock, Toast.LENGTH_SHORT).show();

                    dbHelper.updateProd(new Product(
                            product.getId(),
                            product.getName(),
                            product.getSku(),
                            product.getPrice(),
                            String.valueOf(stock)
                    ));

                    dbHelper.updateStockKeranjang(new Product(
                            product.getId(),
                            product.getName(),
                            product.getSku(),
                            product.getPrice(),
                            String.valueOf(numberPicker.getValue())
                    ));
                }
            });
            alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alert.setNeutralButton("Batalkan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listProduct.remove(product);
                    notifyDataSetChanged();

                    int finalProd = Integer.parseInt(stockProduct.getStock()) + Integer.parseInt(product.getStock());

                    dbHelper.updateProd(new Product(
                            product.getId(),
                            product.getName(),
                            product.getSku(),
                            product.getPrice(),
                            String.valueOf(finalProd)
                    ));
                    dbHelper.deleteItemKeranjang(product.getId());
                }
            });
            alert.create().show();
        }
    }
}
