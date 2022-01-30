package com.example.apptiempo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BizkaiaConsultas extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ConnectionClass connectionClass;
    private Connection con;
    private int idProvinciaBizkaia = 1;
    private String nameMunicipios;
    private Context esto = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bizkaia_consultas);
        linearLayout = findViewById(R.id.linearLayoutt);

        connectionClass = new ConnectionClass();

        try {
            con = connectionClass.CONN();

            String query = "select Name from pueblos where ID_Provincia = "  + "\'" + idProvinciaBizkaia + "\'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                nameMunicipios = rs.getString("Name");

                TextView textViewTareaGeneral = new TextView(this);
                textViewTareaGeneral.setHeight(105);
                textViewTareaGeneral.setTextSize(16);
                textViewTareaGeneral.setText(nameMunicipios);
                insertarOnClick(textViewTareaGeneral);
                linearLayout.addView(textViewTareaGeneral);
            }


        } catch (Exception ex) {
            con = null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionBarOpciones abOpciones = new ActionBarOpciones();
        int id = item.getItemId();

        if (id==R.id.abAcercaDe) {
            abOpciones.abAcercaDe(this);
        }
        if(id == R.id.abHome){
            abOpciones.abHome(this);
        }
        return super.onOptionsItemSelected(item);
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