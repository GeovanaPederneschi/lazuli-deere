package com.example.mechanic.databaseApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseMechanic extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "auto_mechanic";

        SQLiteDatabase db;

        public DatabaseMechanic(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = getWritableDatabase();
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ContractReaderSqlCreateTables.SQL_CREATE_TABLE_EMPLOYEE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ContractReaderSqlCreateTables.SQL_DELETE_TABLE_EMPLOYEE);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public SQLiteDatabase getDb() {
            return db;
        }
}
