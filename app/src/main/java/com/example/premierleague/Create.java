package com.example.premierleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import db.DbHelper;

public class Create extends AppCompatActivity {

    private EditText clubName1, clubName2;
    private ImageView logo1, logo2;
    private Button BtnCreate, BtnCancel, BtnChooseImage1, BtnChooseImage2;
    private EditText tanggal, waktu, deskripsi;
    final int REQUEST_CODE_GALLERY_1 = 999;
    final int REQUEST_CODE_GALLERY_2 = 998;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //FIND VIEW
        clubName1 = findViewById(R.id.insertclubname1);
        clubName2 = findViewById(R.id.insertclubname2);
        logo1 = findViewById(R.id.insertlogo1);
        logo2 = findViewById(R.id.insertlogo2);
        BtnChooseImage1 = findViewById(R.id.insertbtnchooseimage1);
        BtnChooseImage2 = findViewById(R.id.insertbtnchooseimage2);
        BtnCreate = findViewById(R.id.insertbtncreate);
        BtnCancel = findViewById(R.id.insertbtncancel);
        tanggal = findViewById(R.id.inserttanggal);
        waktu = findViewById(R.id.insertwaktu);
        deskripsi = findViewById(R.id.insertdeskripsi);

        dbHelper = new DbHelper(this);

        //INSERT BUTTON CHOOSE IMAGE
        BtnChooseImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Create.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY_1);
            }
        });
        BtnChooseImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Create.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY_2);
            }
        });

        //INSERT BUTTON CREATE
        BtnCreate.setOnClickListener(v -> {
            if (clubName1.getText().toString().isEmpty()) {
                Toast.makeText(Create.this, "Error: Club Name 1 Harus Diisi!", Toast.LENGTH_SHORT).show();
            } else if (clubName2.getText().toString().isEmpty()) {
                Toast.makeText(Create.this, "Error: Club Name 2 Harus Diisi!", Toast.LENGTH_SHORT).show();
            } else if (tanggal.getText().toString().isEmpty()) {
                Toast.makeText(Create.this, "Error: Tanggal Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else if (waktu.getText().toString().isEmpty()) {
                Toast.makeText(Create.this, "Error: Waktu Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else if (deskripsi.getText().toString().isEmpty()) {
                Toast.makeText(Create.this, "Error: Deskripsi Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else {
                dbHelper.createMatch(clubName1.getText().toString(), clubName2.getText().toString(),
                        imageViewToByte(logo1), imageViewToByte(logo2),
                        tanggal.getText().toString(), waktu.getText().toString(), deskripsi.getText().toString());

                clubName1.setText("");
                clubName2.setText("");
                tanggal.setText("");
                waktu.setText("");
                deskripsi.setText("");
                logo1.setImageResource(R.mipmap.ic_launcher);
                logo2.setImageResource(R.mipmap.ic_launcher);
                Toast.makeText(Create.this, "Create a New Match Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Create.this, Mainmenu.class);
                startActivity(intent);
            }
        });

        //INSERT BUTTON CANCEL
        BtnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(Create.this, Mainmenu.class);
            startActivity(intent);
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY_1){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentimage1 = new Intent(Intent.ACTION_PICK);
                intentimage1.setType("image/*");
                startActivityForResult(intentimage1, REQUEST_CODE_GALLERY_1);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }else if(requestCode == REQUEST_CODE_GALLERY_2){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentimage2 = new Intent(Intent.ACTION_PICK);
                intentimage2.setType("image/*");
                startActivityForResult(intentimage2, REQUEST_CODE_GALLERY_2);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY_1 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
                logo1.setImageBitmap(bitmap1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_CODE_GALLERY_2 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
                logo2.setImageBitmap(bitmap2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}