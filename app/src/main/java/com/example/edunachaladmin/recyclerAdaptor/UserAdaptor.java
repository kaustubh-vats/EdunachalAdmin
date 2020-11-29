package com.example.edunachaladmin.recyclerAdaptor;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edunachaladmin.R;
import com.example.edunachaladmin.model.UserModel;

import java.util.List;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.MyUserViewHolder> {
    Context context;
    List<UserModel> userModels;

    public UserAdaptor(Context context, List<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public MyUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_temp,parent,false);
        return new MyUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUserViewHolder holder, int position) {
        UserModel userModel=userModels.get(position);
        holder.textView.setText(userModel.getName());
        holder.textView.setSelected(true);
        String details = "Phone: "+userModel.getPhone()+"\nEmail: "+userModel.getEmail()+"\nDomain of learning: "+userModel.getDomain();
        holder.textView1.setText(details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            if (userModel.getEmail().equals("edunachal@gmail.com") && userModel.getDomain().equals("Admin")) {
                holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_verified_24, 0);
            } else {
                holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class MyUserViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        public MyUserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView4);
            textView1=itemView.findViewById(R.id.textView5);
        }
    }
}
