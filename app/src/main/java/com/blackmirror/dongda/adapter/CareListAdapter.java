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

public class CareListAdapter extends RecyclerView.Adapter<CareListAdapter.CareListViewHolder> {


    private List<Integer> list;
    protected Context context;
    private OnCareListClickListener listener;


    public void setOnCareListClickListener(OnCareListClickListener listener) {
        this.listener = listener;
    }

    public CareListAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public CareListAdapter.CareListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care_list,parent,false);
        return new CareListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CareListAdapter.CareListViewHolder holder, int position) {

            holder.sv_care_list_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get
                    (position)));
        holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
        initListener(holder, position);

    }


    private void initListener(final CareListViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareListClick(holder.itemView, pos);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemCareLikeClick(holder.iv_care_list_like, holder
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


    public static class CareListViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_care_list_photo;
        public ImageView iv_care_list_like;
        public TextView tv_care_list_name;
        public TextView tv_care_list_content;
        public TextView tv_care_list_location;

        public CareListViewHolder(View itemView) {
            super(itemView);
            sv_care_list_photo = itemView.findViewById(R.id.sv_care_list_photo);
            iv_care_list_like = itemView.findViewById(R.id.iv_care_list_like);
            tv_care_list_name = itemView.findViewById(R.id.tv_care_list_name);
            tv_care_list_content = itemView.findViewById(R.id.tv_care_list_content);
            tv_care_list_location = itemView.findViewById(R.id.tv_care_list_location);

        }
    }


    public interface OnCareListClickListener {
        void onItemCareListClick(View view, int position);

        void onItemCareLikeClick(View view, int position);
    }

}
