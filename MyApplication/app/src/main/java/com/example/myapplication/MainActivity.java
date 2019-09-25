package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username = findViewById(R.id.txtName).toString();
        String email = findViewById(R.id.txtEmail).toString();
        String password = findViewById(R.id.txtPassword).toString();


    }
}
