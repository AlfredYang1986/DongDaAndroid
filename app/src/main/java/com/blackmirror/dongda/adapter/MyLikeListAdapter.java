package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.model.serverbean.QueryLikeServerBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyLikeListAdapter extends RecyclerView.Adapter<MyLikeListAdapter.MyLikeListViewHolder> {


    private List<QueryLikeServerBean.ResultBean.ServicesBean> list;
    protected Context context;
    private OnLikeListClickListener listener;


    public void setOnLikeListClickListener(OnLikeListClickListener listener) {
        this.listener = listener;
    }

    public MyLikeListAdapter(Context context, List<QueryLikeServerBean.ResultBean.ServicesBean> list) {
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


        QueryLikeServerBean.ResultBean.ServicesBean servicesBean = list.get(position);

        String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
        holder.sv_care_list_photo.setImageURI(url);

        if (servicesBean.is_collected){
            holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
        }else {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
        }

        holder.tv_care_list_name.setText(list.get(position).service_tags.get(0));

        StringBuilder sb = new StringBuilder();
        if (servicesBean.service_leaf.contains("看顾")){
            sb.append(servicesBean.brand_name)
                    .append("的")
                    .append(servicesBean.service_leaf);
        }else {
            sb.append(servicesBean.brand_name)
                    .append("的")
                    .append(servicesBean.service_leaf)
                    .append(servicesBean.category);
        }


        holder.tv_care_list_content.setText(sb.toString());
        holder.tv_care_list_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf("区")+1));

        initListener(holder, position,servicesBean);

    }

    @Override
    public void onBindViewHolder(MyLikeListViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isLike= (boolean) payloads.get(0);
            list.get(position).is_collected= isLike;
            if (isLike){
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }


    private void initListener(final MyLikeListViewHolder holder, int position,
                              final QueryLikeServerBean.ResultBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemLikeListClick(holder.itemView, pos, servicesBean.service_id);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemLikeClick(holder.iv_care_list_like, holder
                            .getAdapterPosition(), servicesBean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
        void onItemLikeListClick(View view, int position, String service_id);

        void onItemLikeClick(View view, int position, QueryLikeServerBean.ResultBean.ServicesBean
                servicesBean);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        if (position!=list.size()){
            notifyItemRangeChanged(position,list.size()-position);
        }
    }

}
