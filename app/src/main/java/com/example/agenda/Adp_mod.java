package com.example.agenda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adp_mod extends RecyclerView.Adapter<Adp_mod.ViewHolder> {

    String[] modulos= {"Acceso a datos","Desarrollo de interfaces","Ingles técnico para grado superior",
    "Programacion de servicios y procesos","Programacion multimedia y dispositivos",
    "Sistemas de gestion empresarial","Formacion y orientacion laboral"};

    LayoutInflater Inflador;

    SQLiteDatabase db;

    Context c;


    Adp_mod(Context context){
        Inflador = LayoutInflater.from(context);
        this.c=context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.txt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = Inflador.inflate(R.layout.mod,parent,false);

        adminSQL asl = new adminSQL(v.getContext(),"profesores",null,1);
        db=asl.getWritableDatabase();


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String modulo = modulos[position];

        holder.txt.setText(modulo);

        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor f=db.rawQuery("select nombre,horario from profesores where codigo="+position,null);
                if (f.moveToFirst()){
                    AlertDialog.Builder MyAlert = new AlertDialog.Builder(c);
                    MyAlert.setTitle(modulos[position]);
                    MyAlert.setMessage(f.getString(0)+"\n"+f.getString(1));
                    MyAlert.setPositiveButton("Agregar Evento", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(c,New_evento.class);
                            i.putExtra("Modulo",modulos[position]);
                            i.putExtra("cod_profe",position);
                            c.startActivity(i);
                        }
                    });
                    MyAlert.setNegativeButton("Cancelar",null);
                    AlertDialog dialog = MyAlert.create();
                    dialog.show();
                }else{
                    Toast.makeText(c,"Error BD",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return modulos.length;
    }


}
