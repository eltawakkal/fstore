package com.example.kasirmobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductHistory {

    String id;
    String tglTransaksi;
    String name;
    String sku;
    String price;
    String stock;

    public ProductHistory() {
    }

    public ProductHistory(String id, String tglTransaksi, String name, String sku, String price, String stock) {
        this.id = id;
        this.tglTransaksi = tglTransaksi;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(String tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
