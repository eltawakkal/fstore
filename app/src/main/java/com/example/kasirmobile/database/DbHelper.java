package com.example.kasirmobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.kasirmobile.model.Product;
import com.example.kasirmobile.model.ProductHistory;

import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    Context context;
    DbConfig dbConfig;
    SQLiteDatabase database;
    Cursor cursor;

    public DbHelper(Context context) {
        this.context = context;
        dbConfig = new DbConfig(context);
    }

//    PRODUCT TRANSACTION =============================================================================================================================
    public List<Product> getAllProduct(String name) {
        List<Product> products = new ArrayList<>();

        database = dbConfig.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_PRODUCT + " WHERE name LIKE '%" + name + "%'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                products.add(new Product(
                        cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }

        return products;
    }

    public Product getProduct(String id) {
        Product product = new Product();

        database = dbConfig.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_PRODUCT + " WHERE id = '" + id + "'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                product = new Product(
                        cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4)
                );
            } while (cursor.moveToNext());
        }

        return product;
    }

    public Product getProductBySKU(String sku) {
        Product product = new Product();

        database = dbConfig.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_PRODUCT + " WHERE sku = '" + sku + "'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                product = new Product(
                        cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4)
                );
            } while (cursor.moveToNext());
        }

        return product;
    }

    public void addProduct(Product product) {
        database = dbConfig.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sku", product.getSku());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());

        database.insert(DbConfig.TB_PRODUCT, null, values);
    }

    public void deleteProduct(String id) {
        database = dbConfig.getWritableDatabase();

        String where = "id=?";
        String[] whereArg = new String[]{id};

        database.delete(DbConfig.TB_PRODUCT, where, whereArg);
    }

    public void updateProd(Product product) {
        database = dbConfig.getWritableDatabase();

        String where = "id=?";
        String[] whereArg = new String[]{product.getId()};

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());
        values.put("sku", product.getSku());

        database.update(DbConfig.TB_PRODUCT, values, where, whereArg);
    }

//    PRODUCT TRANSACTION =============================================================================================================================

//    KERANJANG TRANSACTION =============================================================================================================================

    public List<Product> getAllBasket(String name) {
        List<Product> products = new ArrayList<>();

        database = dbConfig.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_BASKET + " WHERE name LIKE '%" + name + "%'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                products.add(new Product(
                        cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }

        return products;
    }

    public void addKeranjang(Product product) {
        database = dbConfig.getWritableDatabase();

        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_BASKET + " WHERE id like '" + product.getId() + "'", null);

        if (cursor.getCount() != 0) {
            ContentValues values = new ContentValues();

            cursor.moveToFirst();

            int stock = Integer.parseInt(cursor.getString(4)) + Integer.parseInt(product.getStock());

            values.put("sku", product.getSku());
            values.put("name", product.getName());
            values.put("price", product.getPrice());
            values.put("stock", stock);

            String where = "id=?";
            String[] whereArg = new String[]{product.getId()};

            database.update(DbConfig.TB_BASKET, values, where, whereArg);
        } else {
            ContentValues values = new ContentValues();
            values.put("id", product.getId());
            values.put("sku", product.getSku());
            values.put("name", product.getName());
            values.put("price", product.getPrice());
            values.put("stock", product.getStock());

            database.insert(DbConfig.TB_BASKET, null, values);
        }
    }

    public void updateKeranjang(Product product) {
        database = dbConfig.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("sku", product.getSku());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
//        values.put("stock", product.getStock());

        String where = "id=?";
        String[] whereArg = new String[]{product.getId()};

        database.update(DbConfig.TB_BASKET, values, where, whereArg);
    }

    public void updateStockKeranjang(Product product) {
        database = dbConfig.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("stock", product.getStock());

        String where = "id=?";
        String[] whereArg = new String[]{product.getId()};

        database.update(DbConfig.TB_BASKET, values, where, whereArg);
    }

    public void deleteItemKeranjang(String id) {
        database = dbConfig.getWritableDatabase();

        String where = "id=?";
        String[] whereArg = new String[]{id};

        database.delete(DbConfig.TB_BASKET, where, whereArg);
    }

//    KERANJANG TRANSACTION =============================================================================================================================

//    HISTORY TRANSACTION =============================================================================================================================

    public void addHistory(ProductHistory product) {
        database = dbConfig.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tgl_transaksi", product.getTglTransaksi());
        values.put("sku", product.getSku());
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("stock", product.getStock());

        database.insert(DbConfig.TB_HISTORY, null, values);
    }

    public List<ProductHistory> getAllHistory(String name) {
        List<ProductHistory> products = new ArrayList<>();

        database = dbConfig.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM " + DbConfig.TB_HISTORY + " WHERE name LIKE '%" + name + "%'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            new ProductHistory(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
            );
            do {
                products.add(new ProductHistory(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToPrevious());
        }

        return products;
    }

}
