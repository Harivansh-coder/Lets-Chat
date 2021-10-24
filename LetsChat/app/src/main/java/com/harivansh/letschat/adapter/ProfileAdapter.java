package com.harivansh.letschat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harivansh.letschat.ChatScreen;
import com.harivansh.letschat.R;
import com.harivansh.letschat.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<User> userArrayList;
    private Context context;


    public ProfileAdapter(ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;

    }



    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView userName, lastMessage;

        public ProfileViewHolder(View view){
            super(view);
            profileImage = view.findViewById(R.id.chat_profile_image);
            userName = view.findViewById(R.id.user_name);
            lastMessage = view.findViewById(R.id.last_message);

        }



    }



    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_chat_profile, parent, false);

        return new ProfileViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        User user = userArrayList.get(position);
        Picasso.get().load(user.getUserProfileImage()).
                placeholder(R.drawable.profile).into(holder.profileImage);

        holder.userName.setText(user.getUserName());
        //last message

        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid()+ user.getUserId())
                .orderByChild("messageTime")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                holder.lastMessage.setText(snapshot1.child("messageText").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        holder.lastMessage.setText("last message");
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatScreen.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("userProfileImg",user.getUserProfileImage());
                intent.putExtra("userName",user.getUserName());
                context.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


}
