package com.example.kasirmobile.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.kasirmobile.R;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class ActivityAddBerkala extends AppCompatActivity {

    DecoratedBarcodeView barcodeView;
    EditText edtSku, edtName, edtPrice, edtStock;
    MaterialButton btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_berkala);

        initView();
        cekPermission();
    }

    private void cekPermission() {
        String camera = Manifest.permission.CAMERA;

        if (ContextCompat.checkSelfPermission(this, camera) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{camera}, 12);
            } else {
                startToScan();
            }

        } else {
            startToScan();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startToScan();
        } else {
            Toast.makeText(this, "Anda Tidak Bisa Menggunakan Fitur Ini", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        barcodeView = findViewById(R.id.barcod_view);
        edtSku = findViewById(R.id.edt_add_product_sku_berkala);
        edtName = findViewById(R.id.edt_add_product_name_berkala);
        edtPrice = findViewById(R.id.edt_add_product_price_berkala);
        edtStock = findViewById(R.id.edt_add_product_stock_berkala);
        btnAdd = findViewById(R.id.mbt_add_product_berkala);
    }

    void startToScan() {
        barcodeView.resume();

        BarcodeCallback callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                edtSku.setText(result.getText());
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        };

        barcodeView.decodeContinuous(callback);
    }
}
