package com.example.premierleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import db.DbHelper;
import model.Match;

public class ViewMatch extends AppCompatActivity {

    private DbHelper dbHelper;
    private TextView clubName1, clubName2, tanggal, waktu, deskripsi;
    private ImageView logo1, logo2;
    private Button BtnBack;
    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //FIND VIEW
        clubName1 = findViewById(R.id.clubname_1);
        clubName2 = findViewById(R.id.clubname_2);
        tanggal = findViewById(R.id.tanggal);
        waktu = findViewById(R.id.waktu);
        deskripsi = findViewById(R.id.deskripsi);
        logo1 = findViewById(R.id.clublogo_1);
        logo2 = findViewById(R.id.clublogo_2);
        BtnBack = findViewById(R.id.btnback);

        dbHelper = new DbHelper(this);

        //SET
        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra("matchview");

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

        //EDIT BUTTON DELETE
        BtnBack.setOnClickListener(v -> {
            Intent intenteditcancel = new Intent(ViewMatch.this, Mainmenu.class);
            startActivity(intenteditcancel);
        });
    }
}