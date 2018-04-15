package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyLikeListAdapter extends RecyclerView.Adapter<MyLikeListAdapter.MyLikeListViewHolder> {


    private List<Integer> list;
    protected Context context;
    private OnLikeListClickListener listener;


    public void setOnLikeListClickListener(OnLikeListClickListener listener) {
        this.listener = listener;
    }

    public MyLikeListAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyLikeListAdapter.MyLikeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care_list,parent,false);
        return new MyLikeListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyLikeListAdapter.MyLikeListViewHolder holder, int position) {

        holder.sv_care_list_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get
                (position)));
        holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
        initListener(holder, position);

    }


    private void initListener(final MyLikeListViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemLikeListClick(holder.itemView, pos);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemLikeClick(holder.iv_care_list_like, holder
                            .getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setMoreData(List<Integer> moreList) {
        list.addAll(moreList);
    }


    public static class MyLikeListViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_care_list_photo;
        public ImageView iv_care_list_like;
        public TextView tv_care_list_name;
        public TextView tv_care_list_content;
        public TextView tv_care_list_location;

        public MyLikeListViewHolder(View itemView) {
            super(itemView);
            sv_care_list_photo = itemView.findViewById(R.id.sv_care_list_photo);
            iv_care_list_like = itemView.findViewById(R.id.iv_care_list_like);
            tv_care_list_name = itemView.findViewById(R.id.tv_care_list_name);
            tv_care_list_content = itemView.findViewById(R.id.tv_care_list_content);
            tv_care_list_location = itemView.findViewById(R.id.tv_care_list_location);

        }
    }


    public interface OnLikeListClickListener {
        void onItemLikeListClick(View view, int position);

        void onItemLikeClick(View view, int position);
    }

}
