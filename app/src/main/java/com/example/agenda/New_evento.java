package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class New_evento extends AppCompatActivity {

    TextView mod;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_evento);

        mod=findViewById(R.id.mod);
        spin=findViewById(R.id.spin);

        Bundle extras = getIntent().getExtras();
        mod.setText(extras.getString("Modulo"));
        String cod_profe = extras.getString("cod_profe");
        String[] ev = {"Ausencia del profesor","Practica Obligatoria","Examen","Otros"};

        spin.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,ev));

    }
}