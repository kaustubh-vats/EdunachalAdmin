package com.example.edunachaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachaladmin.model.UserModel;
import com.example.edunachaladmin.recyclerAdaptor.UserAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsers extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<UserModel> userModels;
    UserAdaptor userAdaptor;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);
        progressBar=findViewById(R.id.progressBar5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView=findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                if(snapshot.exists())
                {
                    userModels=new ArrayList<>();
                    userAdaptor=new UserAdaptor(DisplayUsers.this,userModels);
                    recyclerView.setAdapter(userAdaptor);
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        UserModel userModel=ds.getValue(UserModel.class);
                        userModels.add(userModel);
                        userAdaptor.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(DisplayUsers.this, "No User found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayUsers.this, "Error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}