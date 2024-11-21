package com.example.chatapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdaptor extends RecyclerView.Adapter<UsersAdaptor.MyViewHolder> {

    private Context context;

    private List<UserModel> userModellist;

    public UsersAdaptor(Context context) {
        this.context = context;
        this.userModellist = new ArrayList<>();
    }


    public void add(UserModel userModel) {
        userModellist.add(userModel);
        notifyItemInserted(userModellist.size() - 1);
    }

    public void clear() {
        userModellist.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersAdaptor.MyViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_users_adaptor, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdaptor.MyViewHolder parent, int position) {

        UserModel userModel = userModellist.get(position);
        parent.Name.setText(userModel.getUserName());
        parent.Email.setText(userModel.getUseremail());

        parent.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id", userModel.getUserId());
                intent.putExtra("name", userModel.getUserName());
                context.startActivity(intent);
            }
        });

    }

    public List<UserModel> getUserModellist() {
        return userModellist;
    }

    @Override
    public int getItemCount() {
        return userModellist.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            Email = itemView.findViewById(R.id.email);
        }
    }
}