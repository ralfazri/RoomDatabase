package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.roomdatabase.lihat_data.LihatDataActivity;
import com.example.roomdatabase.tambah_data.TambahDataActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tambah =  findViewById(R.id.btn_tambah);
        Button lihat = findViewById(R.id.btn_lihat);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, TambahDataActivity.class);
                startActivity(intent);
            }
        });

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, LihatDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
