package com.example.edunachaladmin.recyclerAdaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachaladmin.R;
import com.example.edunachaladmin.model.PdfModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PdfListAdaptor extends RecyclerView.Adapter<PdfListAdaptor.PdfViewHolder> {
    Context context;
    List<PdfModel> pdfModels;
    String flagExtra, flagExtra1;

    public PdfListAdaptor(Context context, List<PdfModel> pdfModels, String flagExtra, String flagExtra1) {
        this.context = context;
        this.pdfModels = pdfModels;
        this.flagExtra = flagExtra;
        this.flagExtra1 = flagExtra1;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list_temp,parent,false);
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, int position) {
        PdfModel pdfModel=pdfModels.get(position);
        holder.textView.setText(pdfModel.getName());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageButton.setEnabled(false);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(flagExtra).child(flagExtra1).child(pdfModel.getStoragename());
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfModel.getUrl());
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.imageButton.setEnabled(true);
                                pdfModels.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to delete "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                holder.imageButton.setEnabled(true);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        holder.imageButton.setEnabled(true);
                        Toast.makeText(context, "Failed to delete "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfModels.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;
        public PdfViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView6);
            imageButton=itemView.findViewById(R.id.imageButton4);
        }
    }
}
