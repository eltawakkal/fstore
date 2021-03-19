package com.example.kasirmobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;
import com.example.kasirmobile.activity.ActivityAddBerkala;
import com.example.kasirmobile.activity.ActivityAddSatuan;
import com.example.kasirmobile.adapter.RecProdcutAdapter;
import com.example.kasirmobile.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragProduct extends Fragment {

    View rootView;
    RecProdcutAdapter adapter;

    RecyclerView recProduct;
    FloatingActionButton fabAdd;

    List<Product> listProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_product, container, false);

        recProduct = rootView.findViewById(R.id.rec_product_main);
        fabAdd = rootView.findViewById(R.id.fab_add_prodcut);

        listProduct = new ArrayList<>();

        listProduct.add(new Product(
                "0",
                "Ale-ale",
                "2343434",
                "321",
                "2"
        ));
        listProduct.add(new Product(
                "0",
                "Teh Gelas",
                "123",
                "1500",
                "3"
        ));
        listProduct.add(new Product(
                "0",
                "Aqua",
                "2343434",
                "500",
                "4"
        ));

        adapter = new RecProdcutAdapter(getContext(), listProduct);

        recProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        recProduct.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetAddMenu();
            }
        });

        return rootView;
    }

    void showBottomSheetAddMenu() {
        BottomSheetDialog btmSheetMenu = new BottomSheetDialog(getContext());

        View rootMenuView = LayoutInflater.from(getContext()).inflate(R.layout.view_btm_sheet_product, null, false);

        LinearLayout menuAddSatuan = rootMenuView.findViewById(R.id.menu_add_satuan);
        LinearLayout menuAddBerkala = rootMenuView.findViewById(R.id.menu_add_berkala);

        btmSheetMenu.setContentView(rootMenuView);
        btmSheetMenu.show();

        menuAddSatuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityAddSatuan.class));

                btmSheetMenu.dismiss();
            }
        });

        menuAddBerkala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityAddBerkala.class));

                btmSheetMenu.dismiss();
            }
        });
    }
}
