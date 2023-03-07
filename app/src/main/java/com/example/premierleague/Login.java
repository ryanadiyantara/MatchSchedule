package com.example.premierleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText textUsername, textPassword;
    Button login;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);

        login = findViewById(R.id.login);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        sharedPreferences.contains("username");
        sharedPreferences.contains("password");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = textUsername.getText().toString();
                String pw = textPassword.getText().toString();

                if (uname.equals("admin") && pw.equals("123")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", uname);
                    editor.putString("password", pw);
                    editor.apply();
                    intent = new Intent(Login.this, Mainmenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}