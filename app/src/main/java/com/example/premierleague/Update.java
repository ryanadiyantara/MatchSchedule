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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import db.DbHelper;
import model.Match;

public class Update extends AppCompatActivity {

    private DbHelper dbHelper;
    private EditText clubName1, clubName2, tanggal, waktu, deskripsi;
    private ImageView logo1, logo2;
    private Button BtnUpdate, BtnDelete, BtnBack, BtnChooseImage1, BtnChooseImage2;
    private Match match;
    final int REQUEST_CODE_GALLERY_1 = 999;
    final int REQUEST_CODE_GALLERY_2 = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //FIND VIEW
        clubName1 = findViewById(R.id.insertclubname1);
        clubName2 = findViewById(R.id.insertclubname2);
        tanggal = findViewById(R.id.inserttanggal);
        waktu = findViewById(R.id.insertwaktu);
        deskripsi = findViewById(R.id.insertdeskripsi);
        logo1 = findViewById(R.id.insertlogo1);
        logo2 = findViewById(R.id.insertlogo2);
        BtnUpdate = findViewById(R.id.btnupdate);
        BtnDelete = findViewById(R.id.btndelete);
        BtnBack = findViewById(R.id.btncancel);
        BtnChooseImage1 = findViewById(R.id.insertbtnchooseimage1);
        BtnChooseImage2 = findViewById(R.id.insertbtnchooseimage2);

        dbHelper = new DbHelper(this);

        //SET
        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra("matchedit");

        clubName1.setText(match.getMatch_clubname_1());
        clubName2.setText(match.getMatch_clubname_2());
        tanggal.setText(match.getMatch_tanggal());
        waktu.setText(match.getMatch_waktu());
        deskripsi.setText(match.getMatch_deskripsi());
        byte[] clubImage1 = (match.getMatch_clublogo_1());
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(clubImage1, 0, clubImage1.length);
        logo1.setImageBitmap(bitmap1);
        byte[] clubImage2 = (match.getMatch_clublogo_2());
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(clubImage2, 0, clubImage2.length);
        logo2.setImageBitmap(bitmap2);

        //INSERT BUTTON CHOOSE IMAGE
        BtnChooseImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Update.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY_1);
            }
        });
        BtnChooseImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Update.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY_2);
            }
        });

        //EDIT BUTTON UPDATE
        BtnUpdate.setOnClickListener(v -> {
            if (clubName1.getText().toString().isEmpty()) {
                Toast.makeText(Update.this, "Error: Club Name 1 Harus Diisi!", Toast.LENGTH_SHORT).show();
            } else if (clubName2.getText().toString().isEmpty()) {
                Toast.makeText(Update.this, "Error: Club Name 2 Harus Diisi!", Toast.LENGTH_SHORT).show();
            } else if (tanggal.getText().toString().isEmpty()) {
                Toast.makeText(Update.this, "Error: Tanggal Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else if (waktu.getText().toString().isEmpty()) {
                Toast.makeText(Update.this, "Error: Waktu Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else if (deskripsi.getText().toString().isEmpty()) {
                Toast.makeText(Update.this, "Error: Deskripsi Harus Diisi!", Toast.LENGTH_SHORT).show();
            }else {
                dbHelper.updateMatch(match.getMatch_id(), clubName1.getText().toString(), clubName2.getText().toString(),
                        imageViewToByte(logo1), imageViewToByte(logo2),
                        tanggal.getText().toString(), waktu.getText().toString(), deskripsi.getText().toString());
                clubName1.setText("");
                clubName2.setText("");
                tanggal.setText("");
                waktu.setText("");
                deskripsi.setText("");
                logo1.setImageResource(R.mipmap.ic_launcher);
                logo2.setImageResource(R.mipmap.ic_launcher);
                Toast.makeText(Update.this, "Update Match Successfully!", Toast.LENGTH_SHORT).show();
                Intent intentupdate = new Intent(Update.this, Mainmenu.class);
                startActivity(intentupdate);
            }
        });

        //EDIT BUTTON DELETE
        BtnDelete.setOnClickListener(v -> {
            dbHelper.deleteMatch(match.getMatch_id());
            Toast.makeText(Update.this, "Delete Successfully!", Toast.LENGTH_SHORT).show();
            Intent intentdelete = new Intent(Update.this, Mainmenu.class);
            startActivity(intentdelete);
        });

        //EDIT BUTTON BACK
        BtnBack.setOnClickListener(v -> {
            Intent intentback = new Intent(Update.this, Mainmenu.class);
            startActivity(intentback);
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY_1);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }else if(requestCode == REQUEST_CODE_GALLERY_2){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY_2);
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
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                logo1.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_CODE_GALLERY_2 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                logo2.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}