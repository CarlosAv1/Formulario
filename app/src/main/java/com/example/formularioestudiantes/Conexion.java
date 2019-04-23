package com.example.formularioestudiantes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Conexion extends SQLiteOpenHelper {
    public Conexion( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase Escuela) {
        Escuela.execSQL("Create table Alumnos(Matricula int primary key, Nombre text, APaterno text, AMaterno text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
