package com.example.kasirmobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConfig extends SQLiteOpenHelper {

    public static String TB_PRODUCT = "tbProduct";
    public static String TB_BASKET = "tbBasket";
    public static String TB_HISTORY = "tbHistory";

    private static String DATABASE_NAME = "dbToko";
    private static int DATABASE_VERSION = 1;

    private static String CREATE_TB_PRODUCT = "CREATE TABLE tbProduct (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "sku VARCHAR(225), " +
            "name VARCHAR(225)," +
            " price VARCHAR(225)," +
            " stock INTEGER)";

    private static String CREATE_TB_BASKET = "CREATE TABLE tbBasket (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "sku VARCHAR(225), name VARCHAR(225)," +
            " price VARCHAR(225)," +
            " stock INTEGER)";

    private static String CREATE_TB_HISTORY = "CREATE TABLE tbHistory (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tgl_transaksi VARCHAR(225)," +
            "sku VARCHAR(225), name VARCHAR(225)," +
            " price VARCHAR(225)," +
            " stock INTEGER)";

    public DbConfig(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_PRODUCT);
        db.execSQL(CREATE_TB_BASKET);
        db.execSQL(CREATE_TB_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
