package com.example.roomdatabase.lihat_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.roomdatabase.Adapter.MahasiswaAdapter;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.model.ModelMahasiswa;

import java.util.ArrayList;

public class LihatDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MahasiswaAdapter mahasiswaAdapter;

    SwipeRefreshLayout swipe;

    ArrayList<ModelMahasiswa> data;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        swipe = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_mahasiswa);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db_mahasiswa").allowMainThreadQueries().build();


        getSupportActionBar().setTitle("Lihat data mahasiswa");

        getData();
    }

    void getData(){
        swipe.setRefreshing(false);
        data = new ArrayList<>();
        data.clear();
        data.addAll(db.mahasiswaDAO().getMahasiswa());
        mahasiswaAdapter = new MahasiswaAdapter(getApplicationContext(),data);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mahasiswaAdapter);
    }

}
