package com.example.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class adminSQL extends SQLiteOpenHelper {

    String sqlCreate = "create table profesores(codigo int primary key,nombre text,Horario text)";
    String ev = "create table eventos(codigo int primary key,evento text,fecha text,cod_prof int)";

    public adminSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreate);
        /*String sql1="INSERT INTO profesores VALUES (0,'Julio Hornos','L 16:55-20:05 | MA 19:10-21:55');";
        String sql2="INSERT INTO profesores VALUES (1,'Beatriz García-Miguel','L 20:05-21:55 | MI 16:00-17:50 | V 17:50-20:05');";
        String sql3="INSERT INTO profesores VALUES (2,'Roberto Lorente','L 16:00-16:55 | MI 19:10-20:05');";
        String sql4="INSERT INTO profesores VALUES (3,'Jorge Som','MA 16:00-17:50 | J 16:00-17:50');";
        String sql5="INSERT INTO profesores VALUES (4,'Julio Hornos','J 19:10-21:55 | V 16:00-17:50');";
        String sql6="INSERT INTO profesores VALUES (5,'David Mateos','MI 20:05-21:55 | J 21:00-21:55 | V 20:05-21:55');";
        String sql7="INSERT INTO profesores VALUES (6,'Montserrat Beorlegui','MA 17:50-18:45 | MI 17:50-18:45 | J 17:50-18:45)');";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);*/

        db.execSQL(ev);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS profesores");
        db.execSQL(sqlCreate);

        db.execSQL("DROP TABLE IF EXISTS eventos");
        db.execSQL(ev);

    }
}
