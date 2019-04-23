package com.example.formularioestudiantes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText ET_Matricula, ET_Nombre, ET_APaterno, ET_AMaterno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_Matricula = (EditText) findViewById(R.id.Matricula);
        ET_Nombre = (EditText) findViewById(R.id.Nombre);
        ET_APaterno = (EditText) findViewById(R.id.APaterno);
        ET_AMaterno = (EditText) findViewById(R.id.AMaterno);
    }

    public void Registrar(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = ET_Matricula.getText().toString();
        String nombre = ET_Nombre.getText().toString();
        String materno = ET_AMaterno.getText().toString();
        String paterno = ET_APaterno.getText().toString();

        if (!matricula.isEmpty() && !nombre.isEmpty() && !materno.isEmpty() && !paterno.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("Matricula", matricula);
            registro.put("Nombre", nombre);
            registro.put("APaterno", paterno);
            registro.put("AMaterno", materno);

            cn.insert("Alumnos", null, registro);

            cn.close();

            ET_Matricula.setText("");
            ET_Nombre.setText("");
            ET_APaterno.setText("");
            ET_AMaterno.setText("");

            Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Buscar(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = ET_Matricula.getText().toString();

        if (!matricula.isEmpty())
        {
            Cursor fila = cn.rawQuery
                    ("Select Nombre, APaterno, AMaterno from Alumnos WHERE Matricula =" + matricula, null);

            if (fila.moveToFirst())
            {
                ET_Nombre.setText(fila.getString(0));
                ET_APaterno.setText(fila.getString(1));
                ET_AMaterno.setText(fila.getString(2));
                cn.close();
            }
            else
            {
                Toast.makeText(this, "El alumno no está registrado", Toast.LENGTH_SHORT).show();
                cn.close();
            }
        }
        else
        {
                Toast.makeText(this, "Debes ingresar una matricula", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarTodo(View view)
    {
            String registros = "";
            Conexion CN = new Conexion(this, "Escuela", null, 1);
            SQLiteDatabase cn = CN.getWritableDatabase();

            if (cn != null) {

                Cursor cursor = cn.rawQuery("SELECT * FROM Alumnos", null);

                if (cursor.moveToFirst())
                {
                    do
                        {
                        registros += cursor.getInt(cursor.getColumnIndex("Matricula"));
                        registros += " ";
                        registros += cursor.getString(cursor.getColumnIndex("Nombre"));
                        registros += " ";
                        registros += cursor.getString(cursor.getColumnIndex("APaterno"));
                        registros += " ";
                        registros += cursor.getString(cursor.getColumnIndex("AMaterno"));
                        registros += "\n";
                    }
                    while (cursor.moveToNext());
                }
                if (registros.equals(""))
                {
                    Toast.makeText(MainActivity.this, "No hay registros", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    Toast.makeText(MainActivity.this, registros, Toast.LENGTH_LONG).show();
                    cn.close();
                    }
                    cn.close();
            }
    }

    public void Actualizar(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = ET_Matricula.getText().toString();
        String nombre = ET_Nombre.getText().toString();
        String materno = ET_AMaterno.getText().toString();
        String paterno = ET_APaterno.getText().toString();

        if (!matricula.isEmpty() && !nombre.isEmpty() && !materno.isEmpty() && !paterno.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("Matricula", matricula);
            registro.put("Nombre", nombre);
            registro.put("APaterno", paterno);
            registro.put("AMaterno", materno);

            int Actualizados = cn.update
                    ("Alumnos", registro, "Matricula =" + matricula, null);
            cn.close();

            if(Actualizados == 1)
            {
                Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "El alumno no está registrado", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = ET_Matricula.getText().toString();

        if (!matricula.isEmpty())
        {
            int Eliminados = cn.delete
                    ("Alumnos", "Matricula =" + matricula, null);
            cn.close();

            ET_Nombre.setText("");
            ET_Matricula.setText("");
            ET_AMaterno.setText("");
            ET_APaterno.setText("");

            if(Eliminados == 1)
            {
                Toast.makeText(this, "Alumno dado de baja", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "El alumno no está registrado", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Introduce una matricula", Toast.LENGTH_SHORT).show();
        }
    }
}
