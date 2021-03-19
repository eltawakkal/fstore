package com.example.kasirmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kasirmobile.R;
import com.google.android.material.button.MaterialButton;

public class ActivityAddSatuan extends AppCompatActivity {

    EditText edtSku, edtName, edtPrice, edtStock;
    MaterialButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_satuan);

        initView();
    }

    private void initView() {
        edtSku = findViewById(R.id.edt_add_product_sku);
        edtName = findViewById(R.id.edt_add_product_name);
        edtPrice = findViewById(R.id.edt_add_product_price);
        edtStock = findViewById(R.id.edt_add_product_stock);
        btnAdd = findViewById(R.id.mbt_add_product);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();

                finish();
            }
        });
    }

    void addToDatabase() {
        String sku = edtSku.getText().toString();
        String name = edtName.getText().toString();
        String price = edtPrice.getText().toString();
        String stock = edtStock.getText().toString();

        Toast.makeText(this, "sku : " + sku + "\nName: " + name + "\nPrice: " + price + "\nStock : " + stock, Toast.LENGTH_SHORT).show();
    }
}