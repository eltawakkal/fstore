package com.example.kasirmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kasirmobile.database.DbHelper;
import com.example.kasirmobile.fragment.FragHistory;
import com.example.kasirmobile.fragment.FragKeranjang;
import com.example.kasirmobile.fragment.FragProduct;
import com.example.kasirmobile.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DbHelper dbHelper;

    List<Product> listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragProduct()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_product:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragProduct()).commit();
                        break;
                    case R.id.menu_keranjang:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragKeranjang()).commit();
                        break;
                    case R.id.menu_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragHistory()).commit();
                        break;
                }

                return true;
            }
        });
    }
}