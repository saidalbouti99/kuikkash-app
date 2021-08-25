package com.example.myproject;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG=RecyclerViewHolder.class.getSimpleName();

    public TextView tv_job_rv,tv_jobdesc_rv,tv_date_rv,tv_time_rv,tv_price_rv,tv_category;
    public ArrayList<Job> list;
    CardView container;

    public RecyclerViewHolder(@NonNull View itemView, final List<Job> jobObject) {
        super(itemView);
        this.list = list;
        tv_job_rv=itemView.findViewById(R.id.tv_job_rv);
        tv_jobdesc_rv=itemView.findViewById(R.id.tv_jobdesc_rv);

        tv_date_rv=itemView.findViewById(R.id.tv_jobdate_rv);
        tv_time_rv=itemView.findViewById(R.id.tv_jobtime_rv);
        tv_price_rv=itemView.findViewById(R.id.tv_jobprice_rv);
        tv_category=itemView.findViewById(R.id.tv_jobcategory_rv);

        container=itemView.findViewById(R.id.cardvieww);


    }

}
