package com.example.edunachaladmin.recyclerAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachaladmin.R;
import com.example.edunachaladmin.model.CurrentAffairsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentAffairAdaptor extends RecyclerView.Adapter<CurrentAffairAdaptor.CurrentAffairsViewHolder> {
    Context context;
    List<CurrentAffairsModel> currentAffairsModels;

    public CurrentAffairAdaptor(Context context, List<CurrentAffairsModel> currentAffairsModels) {
        this.context = context;
        this.currentAffairsModels = currentAffairsModels;
    }

    @NonNull
    @Override
    public CurrentAffairsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_affairs_temp,parent,false);
        return new CurrentAffairsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentAffairsViewHolder holder, int position) {
        CurrentAffairsModel currentAffairsModel = currentAffairsModels.get(position);
        holder.editText.setText(currentAffairsModel.getTitle());
        holder.editText2.setText(currentAffairsModel.getTag());
        holder.editText3.setText(currentAffairsModel.getContent());
        holder.button.setText(currentAffairsModel.getDate().replace(" ","/"));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Soory you can not change the date", Toast.LENGTH_SHORT).show();
            }
        });
        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title, tag, content;
                title = holder.editText.getText().toString().trim();
                tag = holder.editText2.getText().toString().trim();
                content = holder.editText3.getText().toString().trim();
                if(title.isEmpty())
                {
                    holder.editText.setError("Required field to be displayed as heading of current affairs");
                    return;
                }
                if(tag.isEmpty()){
                    holder.editText2.setError("Required field (e.g. National, International, Sport)");
                    return;
                }
                if(content.isEmpty()){
                    holder.editText3.setError("Required field to be displayed when user click to expand the particular current affair");
                    return;
                }
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.button2.setEnabled(false);
                holder.imageButton.setEnabled(false);
                Map map = new HashMap();
                map.put("title",title);
                map.put("tag",tag);
                map.put("content",content);
                map.put("timestamp", ServerValue.TIMESTAMP);
                holder.databaseReference.child(currentAffairsModel.getDate()).child(currentAffairsModel.getCurId()).updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if(error==null)
                        {
                            currentAffairsModels.get(position).setContent(content);
                            currentAffairsModels.get(position).setTag(tag);
                            currentAffairsModels.get(position).setTitle(title);
                            Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show();
                            holder.progressBar.setVisibility(View.INVISIBLE);
                            holder.button2.setEnabled(true);
                            holder.imageButton.setEnabled(true);
                            notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(context, "Error while updating "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            holder.progressBar.setVisibility(View.INVISIBLE);
                            holder.button2.setEnabled(true);
                            holder.imageButton.setEnabled(true);
                        }
                    }
                });
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.button2.setEnabled(false);
                holder.imageButton.setEnabled(false);
                holder.databaseReference.child(currentAffairsModel.getDate()).child(currentAffairsModel.getCurId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            currentAffairsModels.remove(position);
                            holder.progressBar.setVisibility(View.INVISIBLE);
                            holder.imageButton.setEnabled(true);
                            holder.button2.setEnabled(true);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(context, "Error ehile deleting "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            holder.progressBar.setVisibility(View.INVISIBLE);
                            holder.imageButton.setEnabled(true);
                            holder.button2.setEnabled(true);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentAffairsModels.size();
    }

    public class CurrentAffairsViewHolder extends RecyclerView.ViewHolder{
        EditText editText, editText2, editText3;
        Button button, button2;
        ImageButton imageButton;
        DatabaseReference databaseReference;
        ProgressBar progressBar;
        public CurrentAffairsViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar7);
            editText = itemView.findViewById(R.id.editTextTextPersonName5);
            editText2 = itemView.findViewById(R.id.editTextTextPersonName6);
            editText3 = itemView.findViewById(R.id.editTextTextMultiLine3);
            button = itemView.findViewById(R.id.button14);
            button2 = itemView.findViewById(R.id.button15);
            imageButton = itemView.findViewById(R.id.imageButton3);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("current_affairs").child("universal");
        }
    }
}
