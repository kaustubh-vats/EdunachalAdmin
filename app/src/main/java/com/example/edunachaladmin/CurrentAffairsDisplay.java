package com.example.edunachaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachaladmin.model.CurrentAffairsModel;
import com.example.edunachaladmin.recyclerAdaptor.CurrentAffairAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrentAffairsDisplay extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<CurrentAffairsModel> currentAffairsModels;
    CurrentAffairAdaptor currentAffairAdaptor;
    String curId;
    String date;
    long timestamp;
    String title;
    String content;
    String tag;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("current_affairs").child("universal");
        recyclerView = findViewById(R.id.recyclerView4);
        progressBar=findViewById(R.id.progressBar8);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.addItemDecoration(itemDecorator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        currentAffairsModels = new ArrayList<>();
        currentAffairAdaptor = new CurrentAffairAdaptor(this, currentAffairsModels);
        recyclerView.setAdapter(currentAffairAdaptor);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        date = ds.getKey();
                        for(DataSnapshot dataSnapshot:ds.getChildren())
                        {
                            if(!dataSnapshot.getKey().equals("timestamp"))
                            {
                                curId=dataSnapshot.getKey();
                                title=dataSnapshot.child("title").getValue().toString();
                                content=dataSnapshot.child("title").getValue().toString();
                                tag=dataSnapshot.child("tag").getValue().toString();
                                timestamp=Long.parseLong(dataSnapshot.child("timestamp").getValue().toString());
                                CurrentAffairsModel currentAffairsModel= new CurrentAffairsModel(curId,date,timestamp,title,content,tag);
                                currentAffairsModels.add(currentAffairsModel);
                                currentAffairAdaptor.notifyDataSetChanged();
                            }
                        }
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(CurrentAffairsDisplay.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(CurrentAffairsDisplay.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addCurrentAffair(View view) {
        Intent intent = new Intent(this,AddCurrentAffairs.class);
        startActivity(intent);
    }
}