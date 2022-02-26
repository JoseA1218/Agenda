package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class New_evento extends AppCompatActivity {

    TextView mod;
    Spinner spin;
    Bundle extras;
    CalendarView cal;
    String fecha;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_evento);

        mod=findViewById(R.id.mod);
        spin=findViewById(R.id.spin);
        cal=findViewById(R.id.cal);
        fecha="";


        extras = getIntent().getExtras();
        mod.setText(extras.getString("Modulo"));
        String[] ev = {"Ausencia del profesor","Practica Obligatoria","Examen","Otros"};

        spin.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,ev));

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String num=dayOfMonth+"";
                if(num.length()==1) {
                    fecha = "0"+dayOfMonth + "/" + (month + 1) + "/" + year;
                }else{
                    fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                }


            }
        });

        adminSQL asl = new adminSQL(this,"eventos",null,1);
        db=asl.getWritableDatabase();


    }

    public void agregar(View v) throws ParseException {
        int cod_profe = extras.getInt("cod_profe");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //
        //String fecha;

        if (fecha.equals("")) {
            fecha = sdf.format(new Date(cal.getDate()));
        }



        if (comp(fecha)){
            AlertDialog.Builder MyAlert = new AlertDialog.Builder(v.getContext());
            MyAlert.setTitle("Estas seguro?");
            MyAlert.setMessage("Fecha: "+fecha+"\n Evento: "+spin.getSelectedItem());
            MyAlert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int cont=0;
                    Cursor f=db.rawQuery("select evento,fecha,cod_prof from eventos",null);
                    if (f.moveToFirst()){
                        do {
                            cont++;
                        } while(f.moveToNext());
                    }

                    String sql="INSERT INTO eventos VALUES ("+cont+",'"+spin.getSelectedItem()+"','"+fecha+"',"+extras.getInt("cod_profe")+")";

                    db.execSQL(sql);

                    Toast.makeText(getApplicationContext(),"Evento añadido",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
            });
            MyAlert.setNegativeButton("No",null);
            AlertDialog dialog = MyAlert.create();
            dialog.show();

        }else {
            Toast.makeText(getApplicationContext(),"Seleccione fecha valida",Toast.LENGTH_SHORT).show();
        }



    }

    boolean comp(String fecha) throws ParseException {
        Date hoy = new Date(cal.getDate());
        Date selec = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);


        if (selec.compareTo(hoy) > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}