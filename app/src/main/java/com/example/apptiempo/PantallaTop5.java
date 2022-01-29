package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PantallaTop5 extends AppCompatActivity {

    private Context esto = this;

    private LinearLayout linearLayout;
    private ConnectionClass connectionClass;
    private Connection con;

    private ConnectionClass connectionClass2;
    private Connection con2;

    private TextView textViewProvincias;
    private TextView textViewBizkaia;
    private TextView textViewGipuzkoa;
    private TextView textViewAraba;

    private int numero;
    private String numeroBug;

    private int numeroPueblo;
    private String numeroBugPueblo;

    private String nombrePueblo;

    private ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_top5);

        linearLayout = findViewById(R.id.linearLayoutt);
        textViewProvincias = findViewById(R.id.textViewRegistros);

        textViewBizkaia = findViewById(R.id.textViewBizkaia);
        textViewGipuzkoa = findViewById(R.id.textViewGipuzkoa);
        textViewAraba = findViewById(R.id.textViewAraba);

        connectionClass = new ConnectionClass();

        array = new ArrayList<>();

        try {
            con = connectionClass.CONN();

            String query = "select count(ID_Provincia) from pueblos GROUP BY ID_Provincia";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                numero = rs.getInt("count(ID_Provincia)");
                numeroBug = ""+numero;

                array.add(numeroBug);

            }

            textViewBizkaia.setText(textViewBizkaia.getText() + " " + array.get(0));
            textViewGipuzkoa.setText(textViewGipuzkoa.getText() + " " + array.get(1));
            textViewAraba.setText(textViewAraba.getText() + " " + array.get(2));
            Toast.makeText(this, numeroBug, Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            con = null;
        }

        connectionClass2 = new ConnectionClass();

        try {
            con2 = connectionClass2.CONN();

            String query = "select count(ID_Pueblo),ID_Pueblo from estaciones GROUP BY ID_Pueblo order by count(ID_Pueblo) DESC limit 5";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                numero = rs.getInt("count(ID_Pueblo)");
                numeroBug = ""+numero;

                numeroPueblo = rs.getInt("ID_Pueblo");
                numeroBugPueblo = ""+numeroPueblo;

                String query1 = "select Name from pueblos where ID = "  + "\'" + numeroBugPueblo + "\'";

                Statement st1 = con2.createStatement();
                ResultSet rs1 = st1.executeQuery(query1);

                while (rs1.next()) {

                    nombrePueblo = rs1.getString("Name");

                    TextView textViewTareaGeneral = new TextView(this);
                    textViewTareaGeneral.setText(nombrePueblo + ": " + numeroBug +" estaciones");
                    linearLayout.addView(textViewTareaGeneral);

                }

            }

            Toast.makeText(this, numeroBug, Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            con = null;
        }
    }
}