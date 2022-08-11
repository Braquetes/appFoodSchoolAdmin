package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void qr(View v){
        MenuActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MenuActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void ventas(View v){
        MenuActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MenuActivity.this, VentasActivity.class);
        startActivity(i);
    }
}