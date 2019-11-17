package com.example.roomdatabase.tambah_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomdatabase.R;
import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.lihat_data.LihatDataActivity;
import com.example.roomdatabase.model.ModelMahasiswa;

public class TambahDataActivity extends AppCompatActivity {

    EditText edtnama, edtnim, edtasal;
    Button submit;
    AppDatabase db;
    ModelMahasiswa modelMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        getSupportActionBar().setTitle("Tambah data mahasiswa");



        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"db_mahasiswa").build();


        //buat nampung data wajib cuy
        modelMahasiswa = (ModelMahasiswa) getIntent().getSerializableExtra("data");

        edtnama = findViewById(R.id.edt_nama);
        edtnim = findViewById(R.id.edt_nim);
        edtasal =  findViewById(R.id.edt_asal);
        submit = findViewById(R.id.btn_submit);

        //ini untuk saat di kilik recyclemnya
        if (modelMahasiswa != null){
            edtnim.setText(Integer.toString(modelMahasiswa.getNim()));
            edtnama.setText(modelMahasiswa.getNama());
            edtasal.setText(modelMahasiswa.getAlamat());
            submit.setText("UPDATE");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelMahasiswa.setNim(Integer.parseInt(edtnim.getText().toString()));
                    modelMahasiswa.setNama(edtnama.getText().toString());
                    modelMahasiswa.setAlamat(edtasal.getText().toString());
                    updateMahasiswa(modelMahasiswa);
                    //startActivity(new Intent(TambahDataActivity.this, LihatDataActivity.class)); ini tuh gak usah nanti jadi doyuble

                    finish();
                }
            });

        }else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nim = Integer.parseInt(edtnim.getText().toString());
                    String nama = edtnama.getText().toString();
                    String alamat = edtasal.getText().toString();
                    ModelMahasiswa data = new ModelMahasiswa(nim, nama, alamat);
                    //db.mahasiswaDAO().insertMahasiswa(data);
                    insertMahasiswa(data);
                    finish();
                }
            });
        }

        // gak jadi wkwkwkwk karena biar pas null gak fc, dan data di tambah udah ada gituh
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int nim = Integer.parseInt(edtnim.getText().toString());
//                String nama = edtnama.getText().toString();
//                String alamat = edtasal.getText().toString();
//                ModelMahasiswa data = new ModelMahasiswa(nim, nama, alamat);
//  //              db.mahasiswaDAO().insertMahasiswa(data);
//                insertMahasiswa(data);
//            }
//        });

        //terima data pas saat intent
        modelMahasiswa = (ModelMahasiswa) getIntent().getSerializableExtra("data");
    }


    //proses asynrchronus  = proses di balik layar
    @SuppressLint("StaticFieldLeak")
    public void insertMahasiswa(final ModelMahasiswa modelMahasiswa){
        new AsyncTask<Void,Void,Long>(){

            //persiapan aksi
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }

//            //apabila ada update
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                super.onProgressUpdate(values);
//            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                Toast.makeText(TambahDataActivity.this, "Data berhasil di insert", Toast.LENGTH_SHORT).show();
            }

            //aksi di belakang layar
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.mahasiswaDAO().insertMahasiswa(modelMahasiswa);
                return status;
            }
        }.execute();
    }

    //update mahasiswa
    @SuppressLint("StaticFieldLeak")
    public void updateMahasiswa(final ModelMahasiswa modelMahasiswa){
        new AsyncTask<Void,Void,Long>(){

            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.mahasiswaDAO().updateMahasiswa(modelMahasiswa);
                return status;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                Toast.makeText(TambahDataActivity.this, "Data mahasiswa di update", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
