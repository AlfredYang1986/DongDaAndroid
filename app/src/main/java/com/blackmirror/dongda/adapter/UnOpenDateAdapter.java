package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.model.UnOpenDateBean;

public class UnOpenDateAdapter extends RecyclerView.Adapter<UnOpenDateAdapter.UnOpenViewHolder> {


    protected Context context;
    private UnOpenDateBean bean;
    private OnDateClickListener listener;


    public void setOnDateClickListener(OnDateClickListener listener) {
        this.listener = listener;
    }

    public UnOpenDateAdapter(Context context, UnOpenDateBean bean) {
        this.context = context;
        this.bean = bean;
    }


    @Override
    public UnOpenDateAdapter.UnOpenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_un_open_date,parent,false);
        return new UnOpenViewHolder(view);
    }


    @Override
    public void onBindViewHolder(UnOpenDateAdapter.UnOpenViewHolder holder, int position) {

        if (bean.list.get(position).equals("今")){
            holder.tv_date.setTextColor(Color.parseColor("#FFEB5353"));
        }
        holder.tv_date.setText(bean.list.get(position));

        if (position>6) {
            initListener(holder, position);
        }

    }


    private void initListener(final UnOpenViewHolder holder, final int position) {
        holder.tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.view_line.getVisibility() == View.GONE){
                    holder.view_line.setVisibility(View.VISIBLE);
                    holder.tv_date.setTextColor(Color.parseColor("#FFD9D9D9"));
                    if (listener != null) {
                        int pos = holder.getAdapterPosition();
                        listener.onItemDateClick(holder.itemView, pos-7-bean.firstWeek+1,true);
                    }
                }else {
                    holder.view_line.setVisibility(View.GONE);
                    if (bean.list.get(position).equals("今")){
                        holder.tv_date.setTextColor(Color.parseColor("#FFEB5353"));
                    }else {
                        holder.tv_date.setTextColor(Color.parseColor("#FF404040"));
                    }
                    if (listener != null) {
                        int pos = holder.getAdapterPosition();
                        listener.onItemDateClick(holder.itemView, pos-7-bean.firstWeek+1, false);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return bean == null ? 0 : bean.list.size();
    }



    public static class UnOpenViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date;
        public View view_line;

        public UnOpenViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }


    public interface OnDateClickListener {

        void onItemDateClick(View view, int position,boolean isSelect);
    }


}
