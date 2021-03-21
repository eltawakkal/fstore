package com.example.kasirmobile.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;
import com.example.kasirmobile.adapter.RecBasketAdapter;
import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.model.Product;
import com.example.kasirmobile.model.ProductHistory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Calendar;
import java.util.List;

public class FragKeranjang extends Fragment {

    List<Product> listKeranjang;
    int totalHarga = 0;

    View rootView;
    RecBasketAdapter adapter;
    DbHelper dbHelper;
    Product productFromAlert;

    RecyclerView recKeranjang;
    TextView tvTotalKeranjang;
    MaterialButton btnProses;
    MaterialButton btnReset;
    FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_keranjang, container, false);

        initView();

        return rootView;
    }

    private void initView() {
        dbHelper = new DbHelper(getContext());

        recKeranjang = rootView.findViewById(R.id.rec_keranjang);
        tvTotalKeranjang = rootView.findViewById(R.id.tv_total_harga_keranjang);
        btnProses = rootView.findViewById(R.id.btn_proses_keranjang);
        btnReset = rootView.findViewById(R.id.mbt_reset_basket);
        fabAdd = rootView.findViewById(R.id.fab_add_prod_keranjang);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertScanProd();
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDelete = new AlertDialog.Builder(getContext());

                alertDelete
                        .setTitle("Proses Pembelian")
                        .setMessage("Anda Yakin Melanjutkan Transaksi")
                        .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Product product : listKeranjang) {
                                    dbHelper.deleteItemKeranjang(product.getId());
                                    dbHelper.addHistory(new ProductHistory(
                                            "",
                                            String.valueOf(Calendar.getInstance().getTime()),
                                            product.getName(),
                                            product.getSku(),
                                            product.getPrice(),
                                            product.getStock()
                                    ));
                                }

                                onResume();
                                Toast.makeText(getContext(), "Pembelian Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDelete = new AlertDialog.Builder(getContext());

                alertDelete
                        .setTitle("Reset Keranjang")
                        .setMessage("Ini Akan Menghapus Semua Data Di Keranjang")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (Product product : listKeranjang) {
                                    Product stockProduct = dbHelper.getProduct(product.getId());

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

                                onResume();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });

        initRecData();
    }

    private void showAlertScanProd() {
        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.view_alert_scan_to_add, null, false);

        TextView tvSku, tvName, tvStock, tvHarga;
        NumberPicker numberPicker;
        DecoratedBarcodeView barcodeView;

        tvSku = alertView.findViewById(R.id.tv_sku_alert_add_keranjang);
        tvName = alertView.findViewById(R.id.tv_name_alert_add_keranjang);
        tvStock = alertView.findViewById(R.id.tv_stock_alert_add_keranjang);
        tvHarga = alertView.findViewById(R.id.tv_price_alert_add_keranjang);
        numberPicker = alertView.findViewById(R.id.numpick_alert_add_keranjang);
        barcodeView = alertView.findViewById(R.id.barcode_alert_add_keranjang);

        barcodeView.setStatusText("Mencari Barcode");
        barcodeView.resume();

        BarcodeCallback callback = new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                productFromAlert = dbHelper.getProductBySKU(result.getText());
                if (productFromAlert.getName() != null) {
                    barcodeView.setStatusText(result.getText());
                    tvName.setText(productFromAlert.getName());
                    tvStock.setText(productFromAlert.getStock());
                    tvHarga.setText(productFromAlert.getPrice());

                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(Integer.parseInt(productFromAlert.getStock()));
                } else {
                    barcodeView.setStatusText("Barang Tidak Dikenal");
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        };

        barcodeView.decodeSingle(callback);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert
                .setCancelable(true)
                .setTitle("Tambah Ke Keranjang")
                .setView(alertView)
                .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (productFromAlert.getName() != null) {
                            if (Integer.parseInt(productFromAlert.getStock()) > 0) {
                                int stock = Integer.parseInt(productFromAlert.getStock()) - numberPicker.getValue();

                                dbHelper.updateProd(new Product(
                                        productFromAlert.getId(),
                                        productFromAlert.getName(),
                                        productFromAlert.getSku(),
                                        productFromAlert.getPrice(),
                                        String.valueOf(stock)
                                ));

                                dbHelper.addKeranjang(new Product(
                                        productFromAlert.getId(),
                                        productFromAlert.getName(),
                                        productFromAlert.getSku(),
                                        productFromAlert.getPrice(),
                                        String.valueOf(numberPicker.getValue())
                                ));
                                onResume();
                            } else {
                                Toast.makeText(getContext(), "Stock Kosong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Data Tidak Teredia", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    @Override
    public void onResume() {
        super.onResume();

        initRecData();
        List<Product> list = dbHelper.getAllProduct("");
        if (list.size() == 0) {
            fabAdd.setEnabled(false);
        }
    }

    void initRecData() {
        listKeranjang = dbHelper.getAllBasket("");

        if (listKeranjang.size() == 0) {
            btnProses.setEnabled(false);
            btnReset.setEnabled(false);
        }

        for (Product product : listKeranjang) {
            totalHarga += (Integer.parseInt(product.getPrice()) * Integer.parseInt(product.getStock()));
        }

        tvTotalKeranjang.setText("Rp. " + totalHarga);

        adapter = new RecBasketAdapter(getContext(), listKeranjang);

        recKeranjang.setLayoutManager(new LinearLayoutManager(getContext()));
        recKeranjang.setAdapter(adapter);
    }
}
