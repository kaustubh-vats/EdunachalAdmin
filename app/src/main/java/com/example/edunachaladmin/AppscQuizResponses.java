package com.example.edunachaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edunachaladmin.model.QuizResponseModel;
import com.example.edunachaladmin.recyclerAdaptor.ResponseQuizAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppscQuizResponses extends AppCompatActivity {
    RecyclerView recyclerView;
    List<QuizResponseModel> quizResponseModels;
    ResponseQuizAdaptor responseQuizAdaptor;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    String extraFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appsc_quiz_responses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerView2);
        progressBar=findViewById(R.id.progressBar4);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        extraFlag = getIntent().getStringExtra("flag");
        switch (extraFlag) {
            case "appsc current":
                databaseReference = FirebaseDatabase.getInstance().getReference().child("appsc current quiz response");
                break;
            case "appsc":
                databaseReference = FirebaseDatabase.getInstance().getReference().child("appsc quiz response");
                break;
            case "upsc":
                databaseReference = FirebaseDatabase.getInstance().getReference().child("upsc quiz response");
                break;
            default:
                Toast.makeText(this, "Module Not found", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        quizResponseModels = new ArrayList<>();
        responseQuizAdaptor=new ResponseQuizAdaptor(this,quizResponseModels,extraFlag);
        recyclerView.setAdapter(responseQuizAdaptor);
        databaseReference.orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String name=ds.child("name").getValue().toString();
                        int obtained=Integer.parseInt(ds.child("obtained marks").getValue().toString());
                        int total=Integer.parseInt(ds.child("total marks").getValue().toString());
                        float percentage=Float.parseFloat(ds.child("percentage").getValue().toString());
                        String uid=ds.getKey();
                        QuizResponseModel quizResponseModel=new QuizResponseModel(uid,name,obtained,total,percentage);
                        quizResponseModels.add(quizResponseModel);
                        responseQuizAdaptor.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    Toast.makeText(AppscQuizResponses.this, "No data found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AppscQuizResponses.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}