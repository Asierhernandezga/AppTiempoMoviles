package com.example.apptiempo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstacionesDatos extends AppCompatActivity {

    private Context esto = this;
    private String dato;

    private LinearLayout linearLayout;
    private ConnectionClass connectionClass;
    private Connection con;

    private int iDEstacionExacto;

    private TextView textViewRegistros;
    private float COmgm3;
    private float CO8hmgm3;
    private int NOgm3;
    private int NO2gm3;
    private int NOXgm3;
    private int PM10gm3;
    private int PM25gm3;
    private int SO2gm3;
    private Date date;
    private String HourGMT;
    private String Idbug;


    //Fotos
    private int STORAGE_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btnCamara;
    private Button btnCamara2;
    private ImageView imgView;
    private String s;

    private File imagenArchivo;
    private  String rutaImagen;

    private BitmapDrawable drawable;
    private Bitmap bitmap;

    private String nombreImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones_datos);
        linearLayout = findViewById(R.id.linearLayoutt);
        textViewRegistros = findViewById(R.id.textViewRegistros);


        btnCamara = findViewById(R.id.btnCamara);
        btnCamara2 = findViewById(R.id.btnCamara2);
        imgView = findViewById(R.id.imageView);


        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");

        connectionClass = new ConnectionClass();

        try {
            con = connectionClass.CONN();

            String query = "select ID from estaciones where Name = "  + "\'" + dato + "\'";

           // Toast.makeText(this, dato, Toast.LENGTH_SHORT).show();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                iDEstacionExacto = rs.getInt("ID");
                Idbug=""+iDEstacionExacto;

            }

           // Toast.makeText(this, Idbug, Toast.LENGTH_SHORT).show();

            String query1 = "select Date, HourGMT, COmgm3, CO8hmgm3, NOgm3, NO2gm3, NOXgm3, PM10gm3, PM25gm3, SO2gm3 from registros where ID_estacion = "  + "\'" + Idbug + "\' order by Date DESC";

            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(query1);

            while (rs1.next()) {

                date = rs1.getDate("Date");
                HourGMT = rs1.getString("HourGMT");
                COmgm3 = rs1.getFloat("COmgm3");
                CO8hmgm3 = rs1.getFloat("CO8hmgm3");
                NOgm3 = rs1.getInt("NOgm3");
                NO2gm3 = rs1.getInt("NO2gm3");
                NOXgm3 = rs1.getInt("NOXgm3");
                PM10gm3 = rs1.getInt("PM10gm3");
                PM25gm3 = rs1.getInt("PM25gm3");
                SO2gm3 = rs1.getInt("SO2gm3");

                textViewRegistros.append("***************************\n");
                textViewRegistros.append("Fecha: "+date+"\n");
                textViewRegistros.append("Hora: "+HourGMT+"\n");
                textViewRegistros.append("COmgm3: "+COmgm3+"\n");
                textViewRegistros.append("CO8hmgm3: "+CO8hmgm3+"\n");
                textViewRegistros.append("NOgm3: "+NOgm3+"\n");
                textViewRegistros.append("NO2gm3: "+NO2gm3+"\n");
                textViewRegistros.append("NOXgm3: "+NOXgm3+"\n");
                textViewRegistros.append("PM10gm3: "+PM10gm3+"\n");
                textViewRegistros.append("PM25gm3: "+PM25gm3+"\n");
                textViewRegistros.append("SO2gm3: "+SO2gm3+"\n");

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

    public void abrirMapa(View view){

        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");

        Intent i=new Intent(esto,MapsActivityConsulta.class);
        i.putExtra("nombre", dato);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void abrirCamara(View view) {
        String dato = Idbug;

        Intent i=new Intent(esto,PantallaFoto.class);
        i.putExtra("nombre", dato);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}