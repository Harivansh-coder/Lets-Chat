package com.harivansh.letschat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.harivansh.letschat.R;
import com.harivansh.letschat.model.User;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private ArrayList<User> userArrayList;
    private Context context;

    //private ProfileViewClickListener listener;

    public ProfileAdapter(ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }



    public class ProfileViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView userName, lastMessage;

        public ProfileViewHolder(View view){
            super(view);
            profileImage = view.findViewById(R.id.profile_image);
            userName = view.findViewById(R.id.user_name);
            lastMessage = view.findViewById(R.id.last_message);
            //view.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            listener.onClick(v,getAdapterPosition());
//        }

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

    }



    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

//    public interface ProfileViewClickListener{
//        void onClick(View view, int position);
//    }
}
