package com.example.edunachaladmin.recyclerAdaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachaladmin.R;
import com.example.edunachaladmin.model.QuizResponseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ResponseQuizAdaptor extends RecyclerView.Adapter<ResponseQuizAdaptor.MyResponseViewHolder> {
    Context context;
    List<QuizResponseModel> quizResponseModels;
    String extraFlag;

    public ResponseQuizAdaptor(Context context, List<QuizResponseModel> quizResponseModels, String extraFlag) {
        this.context = context;
        this.quizResponseModels = quizResponseModels;
        this.extraFlag = extraFlag;
    }

    @NonNull
    @Override
    public MyResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_response_temp,parent,false);
        return new MyResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyResponseViewHolder holder, int position) {
        QuizResponseModel quizResponseModel=quizResponseModels.get(position);
        holder.textView.setText(quizResponseModel.getName());
        holder.textView1.setText(String.valueOf(quizResponseModel.getObtained()));
        if(quizResponseModel.getPercentage()<60)
        {
            holder.textView1.setTextColor(Color.RED);
        }
        else
        {
            holder.textView1.setTextColor(Color.parseColor("#43e97b"));
        }
        holder.textView2.setText("/"+quizResponseModel.getTotal()+" Percentage: "+quizResponseModel.getPercentage());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.databaseReference.child(quizResponseModel.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            quizResponseModels.remove(position);
                            Toast.makeText(context, "Response Deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(context, "Error while deleting: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizResponseModels.size();
    }

    public class MyResponseViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1,textView2;
        ImageButton imageButton;
        DatabaseReference databaseReference;
        public MyResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            textView1=itemView.findViewById(R.id.textView2);
            textView2=itemView.findViewById(R.id.textView3);
            imageButton=itemView.findViewById(R.id.imageButton2);
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
                    Toast.makeText(context, "Module Not found", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                    break;
            }
        }
    }
}
