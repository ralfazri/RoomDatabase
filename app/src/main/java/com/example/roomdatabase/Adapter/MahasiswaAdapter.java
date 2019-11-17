package com.example.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.roomdatabase.R;
import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.model.ModelMahasiswa;
import com.example.roomdatabase.tambah_data.TambahDataActivity;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    ArrayList<ModelMahasiswa> data;
    AppDatabase db;

    public MahasiswaAdapter(Context context, ArrayList<ModelMahasiswa> data) {
        this.data = data;
        this.context = context;

        db = Room.databaseBuilder(context, AppDatabase.class, "db_mahasiswa").allowMainThreadQueries().build();
    }

    Context context;
    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, final int position) {
        holder.nama.setText(data.get(position).getNama());
        holder.nim.setText(Integer.toString(data.get(position).getNim()));
        holder.alamat.setText(data.get(position).getAlamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelMahasiswa modelMahasiswa =  db.mahasiswaDAO().detailMahasiswa(data.get(position).getIdMahasiswa());
                Intent intent = new Intent(context, TambahDataActivity.class);
                intent.putExtra("data", modelMahasiswa);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Yakin ingin menghapus data ?");
                alertDialog.setMessage("Piilih OK untuk menghapus data").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMahasiswa(position);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.create();
                alertDialog.show();
                return false;
            }
        });
    }

    public void deleteMahasiswa(int position){
        db.mahasiswaDAO().deleteMahasiswa(data.get(position));
        data.remove(position);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, data.size());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder {

        TextView nama, nim, alamat;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaMahasiswa_item);
            nim = itemView.findViewById(R.id.nimMahasiswa_item);
            alamat = itemView.findViewById(R.id.alamatMahasiswa_item);
        }
    }
}
