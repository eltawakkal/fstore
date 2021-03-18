package com.example.kasirmobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;
import com.example.kasirmobile.adapter.RecProdcutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragProduct extends Fragment {

    View rootView;
    RecProdcutAdapter adapter;

    RecyclerView recProduct;
    FloatingActionButton fabAdd;

    List<String> listProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_product, container, false);

        recProduct = rootView.findViewById(R.id.rec_product_main);
        fabAdd = rootView.findViewById(R.id.fab_add_prodcut);

        listProduct = new ArrayList<>();
        listProduct.add("tomat");
        listProduct.add("wortel");
        listProduct.add("pisang");
        listProduct.add("apel");
        listProduct.add("jeruk");

        adapter = new RecProdcutAdapter(getContext(), listProduct);

        recProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recProduct.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProduct.add("Ini Baru");
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
