package com.example.apptiempo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PantallaPrincipal extends AppCompatActivity {

    private Button btnAbrirPermisos;
    private String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btnAbrirPermisos = findViewById(R.id.btnAbrirPermisos);

        PERMISSIONS = new String[] {

                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CAMERA
        };

    }

    public void abrirMapa(View view){
        Intent i = new Intent(this, MapsActivity.class );
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void abrirFoto(View view){
        Intent i = new Intent(this, PantallaFoto.class );
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void activarPermisos(View view){
        if (!hasPermissions(PantallaPrincipal.this,PERMISSIONS)) {
            ActivityCompat.requestPermissions(PantallaPrincipal.this,PERMISSIONS,1);
        }
    }

    private boolean hasPermissions(Context context, String... PERMISSIONS) {

        if (context != null && PERMISSIONS != null) {

            for (String permission: PERMISSIONS){

                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Calling Permission is granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Calling Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission is granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "SMS Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission is granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Camera Permission is denied", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}