/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.Boolean
 *  java.lang.String
 */
package com.example.mogo_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper
extends SQLiteOpenHelper {
    public static final String BD_NAME = "Login.db";
    public static final int DATABASE_VERSION = 1;
    private static final Context context = null;

    public DBHelper(Context context) {
        super(context, BD_NAME, null, 1);
    }

    public Boolean checkUserExists(String string2) {
        if (this.getWritableDatabase().rawQuery("Select * from users where username = ?", new String[]{string2}).getCount() > 0) {
            return true;
        }
        return false;
    }

    public Boolean checkUserPassword(String string2, String string3) {
        if (this.getWritableDatabase().rawQuery("Select * from users where username=? and password=?", new String[]{string2, string3}).getCount() > 0) {
            return true;
        }
        return false;
    }

    public Boolean insertData(String string2, String string3) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", string2);
        contentValues.put("password", string3);
        if (sQLiteDatabase.insert("users", null, contentValues) == -1L) {
            return false;
        }
        return true;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE USERS(username TEXT primary key, password TEXT )");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("drop table if exists users");
    }
}

