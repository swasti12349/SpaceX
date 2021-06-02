package com.sro.spacex;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class rAdapter extends RecyclerView.Adapter<rAdapter.MyViewHolder> {
    private Context context;
    private List<Model> list;
    private RoomDB database;


    public rAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public rAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.useritem, parent, false);
        return new rAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rAdapter.MyViewHolder holder, int position) {
        Model model = list.get(position);

        database = RoomDB.getInstance(context);

        holder.name.setText(model.getName());
        holder.agency.setText(model.getAgency());
        holder.url.setText(model.getWikipedia());
        holder.active.setText(model.getStatus());
        Glide.with(context).load(model.getImage()).into(holder.userImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WikipediaActivity.class);
                intent.putExtra("url", model.getWikipedia());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        Log.d("listl", String.valueOf(list.size()));
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, agency, url, active;
        ImageView userImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            userImage = itemView.findViewById(R.id.img);
            agency = itemView.findViewById(R.id.agency);
            url = itemView.findViewById(R.id.wiki);
            active = itemView.findViewById(R.id.status);

        }
    }
}