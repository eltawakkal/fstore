package com.example.kasirmobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirmobile.R;
import com.example.kasirmobile.adapter.RecHistoryAdapter;
import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.model.Product;
import com.example.kasirmobile.model.ProductHistory;

import java.util.List;

public class FragHistory extends Fragment {

    View rootView;
    RecyclerView recHistory;
    DbHelper dbHelper;
    RecHistoryAdapter adapter;
    List<ProductHistory> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_history, container, false);

        initView();

        return rootView;
    }

    private void initView() {
        dbHelper = new DbHelper(getContext());

        recHistory = rootView.findViewById(R.id.rec_history);

        initRecData();
    }

    private void initRecData() {
        productList = dbHelper.getAllHistory("");

        adapter = new RecHistoryAdapter(getContext(), productList);

        recHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recHistory.setAdapter(adapter);
    }
}
