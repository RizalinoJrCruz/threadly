package com.example.threadlyv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {

    private Context context;
    private List<Notif> notifList;

    public NotifAdapter(Context context, List<Notif> notifList){
        this.context = context;
        this.notifList = notifList;
    }

    public static class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView username,postTitle;
        ImageView profilePicture;
        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.textUsername);
            postTitle = itemView.findViewById(R.id.textPostTitle);
            profilePicture = itemView.findViewById(R.id.imageProfilePicture);
        }
    }


    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notif_post, parent, false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
       Notif notif = notifList.get(position);

       holder.username.setText(notif.getUserName());
       holder.postTitle.setText(notif.getPostTitle());
       holder.profilePicture.setImageResource(R.drawable.ic_default_pfp);
    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }


}

