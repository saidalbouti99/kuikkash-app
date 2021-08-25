package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.RecyclerViewHolder>{

    private ArrayList<Job> jobs;
    protected Context context;

    public RecyclerViewAdapter( Context context,ArrayList<Job> jobs) {

        this.context = context;
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerViewHolder viewHolder;
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list,parent,false);
        viewHolder=new RecyclerViewHolder(layoutView,jobs);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.tv_job_rv.setText(jobs.get(position).getJob());
        holder.tv_jobdesc_rv.setText(jobs.get(position).getJobDesc());
        holder.tv_date_rv.setText(jobs.get(position).getJobDate());
        holder.tv_time_rv.setText(jobs.get(position).getJobTime());
        holder.tv_price_rv.setText(jobs.get(position).getJobPrice());
      holder.container.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
      holder.tv_category.setText(jobs.get(position).getJobCategory());

    }

    @Override
    public int getItemCount() {
      return this.jobs.size();

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private  final String TAG=RecyclerViewHolder.class.getSimpleName();

        public TextView tv_job_rv,tv_jobdesc_rv,tv_date_rv,tv_time_rv,tv_price_rv,tv_category,tv_name_detail;
        public ArrayList<Job> list;
        List<Job> data;
        CardView container;

        public RecyclerViewHolder(@NonNull View itemView, final List<Job> jobObject) {
            super(itemView);
            this.list = list;
            tv_job_rv=itemView.findViewById(R.id.tv_job_rv);
            tv_jobdesc_rv=itemView.findViewById(R.id.tv_jobdesc_rv);
            tv_name_detail = itemView.findViewById(R.id.tv_name_detail);

            tv_date_rv=itemView.findViewById(R.id.tv_jobdate_rv);
            tv_time_rv=itemView.findViewById(R.id.tv_jobtime_rv);
            tv_price_rv=itemView.findViewById(R.id.tv_jobprice_rv);
            tv_category=itemView.findViewById(R.id.tv_jobcategory_rv);

            container=itemView.findViewById(R.id.cardvieww);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i  = new Intent(v.getContext(), DetailActivity.class);
                    i.putExtra("title",jobs.get(getAdapterPosition()).getJob());
                    i.putExtra("category",jobs.get(getAdapterPosition()).getJobCategory());
                    i.putExtra("description",jobs.get(getAdapterPosition()).getJobDesc());
                    i.putExtra("Date",jobs.get(getAdapterPosition()).getJobDate());
                    i.putExtra("Price",jobs.get(getAdapterPosition()).getJobPrice());

                    v.getContext().startActivity(i);


                }
            });

        }

    }
}
