package com.example.kasirmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kasirmobile.R;
import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.model.Product;
import com.google.android.material.button.MaterialButton;

public class ActivityAddSatuan extends AppCompatActivity {

    EditText edtSku, edtName, edtPrice, edtStock;
    MaterialButton btnAdd;
    Product product;

    DbHelper dbHelper;

    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_satuan);

        initView();
    }

    private void initView() {
        dbHelper = new DbHelper(this);
        product = getIntent().getParcelableExtra("product");

        edtSku = findViewById(R.id.edt_add_product_sku);
        edtName = findViewById(R.id.edt_add_product_name);
        edtPrice = findViewById(R.id.edt_add_product_price);
        edtStock = findViewById(R.id.edt_add_product_stock);
        btnAdd = findViewById(R.id.mbt_add_product);

        if (product != null) {
            isEdit = true;

            btnAdd.setText("Perbarui");

            edtSku.setText(product.getSku());
            edtName.setText(product.getName());
            edtPrice.setText(product.getPrice());
            edtStock.setText(product.getStock());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });
    }

    void addToDatabase() {
        String sku = edtSku.getText().toString();
        String name = edtName.getText().toString();
        String price = edtPrice.getText().toString();
        String stock = edtStock.getText().toString();

        Product newProduct;

        if (isEdit) {
             newProduct = new Product(
                    this.product.getId(),
                    name,
                    sku,
                    price,
                    stock
            );
        } else {
            newProduct = new Product(
                    "0",
                    name,
                    sku,
                    price,
                    stock
            );
        }

        if (isEdit) {
            dbHelper.updateProd(newProduct);
            dbHelper.updateKeranjang(newProduct);

        } else {
            dbHelper.addProduct(newProduct);
        }

        finish();
    }
}