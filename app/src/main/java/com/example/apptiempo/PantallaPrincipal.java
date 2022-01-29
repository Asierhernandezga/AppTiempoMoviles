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

    private String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        PERMISSIONS = new String[] {

                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
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

    public void abrirProvincias(View view){
        Intent i = new Intent(this, Provincias.class );
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void abrirTop5(View view){
        Intent i = new Intent(this, PantallaTop5.class );
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
                Toast.makeText(this, R.string.permisosCamaraAceptado, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.permisosCamaraDenegado, Toast.LENGTH_SHORT).show();
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permisosLocalizacionCoarseAceptada, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.permisosLocalizacionCoarseDenegada, Toast.LENGTH_SHORT).show();
            }
            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permisosLocalizacionFineAceptada, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.permisosLocalizacionFineDenegada, Toast.LENGTH_SHORT).show();
            }
            if (grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permisosEscrituraArchivosAceptada, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.permisosEscrituraArchivosDenegada, Toast.LENGTH_SHORT).show();
            }
            if (grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permisosLecturaArchivosAceptada, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, R.string.permisosLecturaArchivosDenegada, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}