package com.example.bloodchart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private Context _context;
    private ArrayList<String > _id, _date, _time, _sbp, _dbp;

    public RecycleAdapter(Context context, ArrayList<String> id, ArrayList<String> date, ArrayList<String> time, ArrayList<String> sbp, ArrayList<String> dbp) {
        _context = context;
        _id = id;
        _date = date;
        _time = time;
        _sbp = sbp;
        _dbp = dbp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View view = inflater.inflate(R.layout.my_recycle_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_id.setText(String.valueOf(_id.get(position)));
        holder.tv_date.setText(String.valueOf(_date.get(position)));
        holder.tv_time.setText(String.valueOf(_time.get(position)));
        holder.tv_sbp.setText(String.valueOf(_sbp.get(position)));
        holder.tv_dbp.setText(String.valueOf(_dbp.get(position)));
    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_date, tv_time, tv_sbp, tv_dbp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_sbp = itemView.findViewById(R.id.tv_sbp);
            tv_dbp = itemView.findViewById(R.id.tv_dbp);

        }
    }
}
