package com.example.edunachaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachaladmin.model.PdfModel;
import com.example.edunachaladmin.recyclerAdaptor.PdfListAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayPdf extends AppCompatActivity {
    DatabaseReference databaseReference;
    List<PdfModel> pdfModels;
    PdfListAdaptor pdfListAdaptor;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    String flagExtra, flagExtra1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pdf);
        progressBar = findViewById(R.id.progressBar9);
        recyclerView = findViewById(R.id.recyclerView5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flagExtra = getIntent().getStringExtra("flagExtra");
        flagExtra1 = getIntent().getStringExtra("flagExtra1");
        databaseReference = FirebaseDatabase.getInstance().getReference().child(flagExtra).child(flagExtra1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pdfModels = new ArrayList<>();
        pdfListAdaptor = new PdfListAdaptor(this,pdfModels,flagExtra,flagExtra1);
        recyclerView.setAdapter(pdfListAdaptor);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String name = ds.child("name").getValue().toString();
                        String url = ds.child("url").getValue().toString();
                        String storageName = ds.getKey();
                        PdfModel pdfModel = new PdfModel(name,url,storageName);
                        pdfModels.add(pdfModel);
                        pdfListAdaptor.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(DisplayPdf.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayPdf.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void uploadNewPdf(View view) {
        Intent intent = new Intent(this,UploadPdf.class);
        intent.putExtra("flagExtra",flagExtra);
        intent.putExtra("flagExtra1",flagExtra1);
        startActivity(intent);
    }
}