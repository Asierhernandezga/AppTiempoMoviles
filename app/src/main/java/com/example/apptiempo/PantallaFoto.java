package com.example.apptiempo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PantallaFoto extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btnCamara;
    private ImageView imgView;
    private ConnectionClass connectionClass;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foto);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);

        connectionClass = new ConnectionClass();

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Permiso almacenamiento
                if (ContextCompat.checkSelfPermission(PantallaFoto.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestStoragePermission();
                }
                //Permiso almacenamiento

              PantallaFotoPermissionsDispatcher.openCameraWithPermissionCheck(PantallaFoto.this);

            }
        });
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void openCamera() {
        Toast.makeText(this, R.string.abriendoCamara, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(intent);
        startActivityForResult(intent, 1);
        //onActivityResult(1,1, intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Permiso almacenamiento

        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permisoAceptado, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.permisoDenegado, Toast.LENGTH_SHORT).show();
            }
        }

        //Permiso almacenamiento

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PantallaFotoPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permisoNecesario)
                .setMessage(R.string.permisoNecesarioCamaraDescripcion)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(this, R.string.toastPermisoDenegado, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onNeverAskAgain() {
        Toast.makeText(this, R.string.toastNoVolverAPreguntar, Toast.LENGTH_SHORT).show();
    }

    //Permiso almacenamiento
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.permisoNecesario)
                    .setMessage(R.string.permisoNecesarioAlmacenamientoDescripcion)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PantallaFoto.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    //Permiso almacenamiento

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File foto = new File(getExternalFilesDir(null), "hola");
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);

            try{
                Connection con = connectionClass.CONN();

               // PreparedStatement ps = con.prepareStatement("insert into lugares_usuario(ID_Espacio,ID_Usuario,Foto,Favorito) values(?,?,?,?)");

                String sql = "INSERT INTO lugares_usuario (ID_Espacio, ID_Usuario,Foto,Favorito) VALUES(?,?,?,?)";

                Statement st2 = con.createStatement();

                ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                imgBitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
                byte[] blob = baos.toByteArray();

                SQLiteStatement insert = st2.compileStatement(sql);
                insert.execute(1);
                ps.setInt(2, 1);
                insert.bindBlob(3, blob);
                ps.setInt(4,1);
                ps.executeUpdate();


                st2.executeUpdate(sql);

                insert.clearBindings();
                insert.bindBlob(2, blob);
                insert.executeInsert();
                db.close();



                Toast.makeText(this, R.string.toastNoVolverAPreguntar, Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}