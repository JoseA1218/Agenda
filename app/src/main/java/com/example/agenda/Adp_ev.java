package com.example.agenda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Adp_ev extends RecyclerView.Adapter<Adp_ev.ViewHolder>{

    ArrayList<String> evento = new ArrayList<String>();
    ArrayList<String> profesor = new ArrayList<String>();
    ArrayList<String> horario = new ArrayList<String>();
    ArrayList<String> fecha = new ArrayList<String>();
    ArrayList<String> cod_prof = new ArrayList<String>();
    ArrayList<String> asig = new ArrayList<String>();


    String[] modulos= {"Acceso a datos","Desarrollo de interfaces","Ingles técnico para grado superior",
            "Programacion de servicios y procesos","Programacion multimedia y dispositivos",
            "Sistemas de gestion empresarial","Formacion y orientacion laboral"};

    LayoutInflater inflador;

    SQLiteDatabase db_ev;
    SQLiteDatabase db_pr;

    Context c;

    int num;

    Adp_ev(Context context){
        inflador = LayoutInflater.from(context);
        this.c = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView asig;
        TextView ev;
        TextView prof;
        TextView hor;
        TextView fec;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            asig=itemView.findViewById(R.id.asig);
            ev=itemView.findViewById(R.id.ev);
            prof=itemView.findViewById(R.id.prof);
            hor=itemView.findViewById(R.id.hor);
            fec=itemView.findViewById(R.id.fec);
            cv=itemView.findViewById(R.id.cv);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.event,parent,false);

        adminSQL asl = new adminSQL(v.getContext(),"eventos",null,1);
        db_ev=asl.getWritableDatabase();

        adminSQL asl2 = new adminSQL(v.getContext(),"profesores",null,1);
        db_pr=asl2.getWritableDatabase();

        Cursor f=db_ev.rawQuery("select evento,fecha,cod_prof from eventos",null);
        if (f.moveToFirst()){
            int cont=0;
            do {

                evento.add(cont,f.getString(0));
                fecha.add(cont,f.getString(1));
                cod_prof.add(cont,f.getString(2));
                cont++;
            } while(f.moveToNext());
        }

        if (cod_prof!=null) {
            for (int i = 0; i < cod_prof.size(); i++) {
                Cursor f2 = db_pr.rawQuery("select nombre,horario from profesores where codigo=" + cod_prof.get(i), null);
                if (f2.moveToFirst()) {
                    asig.add(modulos[Integer.parseInt(cod_prof.get(i))]);
                    profesor.add(i,f2.getString(0));
                    horario.add(i,f2.getString(1));
                }
                f2.close();
            }

        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(db_ev!=null) {
            holder.asig.setText(asig.get(position));
            holder.hor.setText(horario.get(position));
            holder.fec.setText(fecha.get(position));
            holder.prof.setText(profesor.get(position));
            holder.ev.setText(evento.get(position));

            if (!comp(fecha.get(position))){
                holder.cv.setBackgroundColor(Color.RED);
            }

            holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    db_ev.execSQL("DELETE FROM eventos WHERE evento='"+evento.get(position)+"' and fecha='"+fecha.get(position)+"' and cod_prof="+cod_prof.get(position));

                    evento.remove(position);
                    profesor.remove(position);
                    horario.remove(position);
                    fecha.remove(position);
                    cod_prof.remove(position);
                    asig.remove(position);

                    notifyItemRemoved(position);
                    return true;
                }
            });

        }

    }

    @Override
    public int getItemCount() {

        adminSQL asl = new adminSQL(c,"eventos",null,1);
        db_ev=asl.getWritableDatabase();

        Cursor f=db_ev.rawQuery("select count(*) from eventos",null);

        if (f.moveToFirst()) {
            num=Integer.parseInt(f.getString(0));
        }

        return num;
    }

    boolean comp(String fecha) {
        try {
            Date hoy = Calendar.getInstance().getTime();
            Date selec = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);


            if (selec.compareTo(hoy) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


}
