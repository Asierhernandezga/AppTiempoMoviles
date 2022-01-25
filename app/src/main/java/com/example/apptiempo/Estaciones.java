package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Estaciones extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ConnectionClass connectionClass;
    private Connection con;
    private Connection con1;

    private int idProvinciaBizkaia = 1;
    private int iDMunicipioExacto;
    private String estacionExacta;
    private Context esto = this;
    private TextView textViewTareaGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones);
        linearLayout = findViewById(R.id.linearLayoutt);

        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");

        connectionClass = new ConnectionClass();

        try {
            con = connectionClass.CONN();

            String query = "select ID from pueblos where Name = "  + "\'" + dato + "\'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                iDMunicipioExacto = rs.getInt("ID");


            }

            String query1 = "select Name from estaciones where ID_Pueblo = "  + "\'" + iDMunicipioExacto + "\'";

            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(query1);


            while (rs1.next()) {

                estacionExacta = rs1.getString("Name");

                TextView textViewTareaGeneral = new TextView(this);
                textViewTareaGeneral.setHeight(105);
                textViewTareaGeneral.setTextSize(16);
                textViewTareaGeneral.setText(estacionExacta);
                insertarOnClick(textViewTareaGeneral);
                linearLayout.addView(textViewTareaGeneral);

            }


        } catch (Exception ex) {
            con = null;
        }

    }

    private void insertarOnClick(TextView textViewTareaGeneral) {
        textViewTareaGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(esto,Estaciones.class);
                i.putExtra("nombre", textViewTareaGeneral.getText().toString());
                startActivity(i);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}