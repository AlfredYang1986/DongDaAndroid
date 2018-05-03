package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.model.serverbean.HomeInfoServerBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeArtAdapter extends RecyclerView.Adapter<HomeArtAdapter.HomeArtViewHolder> {


    HomeInfoServerBean.ResultBean.HomepageServicesBean bean;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeArtAdapter(Context context, HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public HomeArtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_home_art,parent,false);
        return new HomeArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeArtViewHolder holder, int position) {
        HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean = this.bean.services.get(position);

        String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
        holder.sv_item_art_photo.setImageURI(url);

        if (servicesBean.is_collected){
            holder.iv_item_art_like.setBackgroundResource(R.drawable.like_selected);
        }else {
            holder.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like);
        }

        if (TextUtils.isEmpty(bean.services.get(position).operation.get(0))){
            holder.tv_item_art_name.setVisibility(View.GONE);
        }else {
            holder.tv_item_art_name.setVisibility(View.VISIBLE);
            holder.tv_item_art_name.setText(bean.services.get(position).operation.get(0));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(servicesBean.service_leaf)
                .append(servicesBean.category);
        holder.tv_item_art_detail.setText(sb.toString());
        holder.tv_item_art_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf(context.getString(R.string.str_qu))+1));
        initListener(holder, position,servicesBean);

    }

    @Override
    public void onBindViewHolder(HomeArtViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isLike= (boolean) payloads.get(0);
            bean.services.get(position).is_collected= isLike;
            if (isLike){
                holder.iv_item_art_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                holder.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final HomeArtViewHolder holder, final int position, final HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean) {
        holder.iv_item_art_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onArtLikeClick(holder.iv_item_art_like, pos,servicesBean);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView,holder.getAdapterPosition(),servicesBean.service_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean.services != null) {
            return bean.services.size();
        }
        return 0;
    }

    public void setRefreshData(List<HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean> list){
        bean.services.clear();
        bean.services.addAll(list);
        notifyDataSetChanged();
    }

    public static class HomeArtViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_item_art_photo;
        public ImageView iv_item_art_like;
        public TextView tv_item_art_name;
        public TextView tv_item_art_detail;
        public TextView tv_item_art_location;

        public HomeArtViewHolder(View itemView) {
            super(itemView);
            sv_item_art_photo = itemView.findViewById(R.id.sv_item_art_photo);
            iv_item_art_like = itemView.findViewById(R.id.iv_item_art_like);
            tv_item_art_name = itemView.findViewById(R.id.tv_item_art_name);
            tv_item_art_detail = itemView.findViewById(R.id.tv_item_art_detail);
            tv_item_art_location = itemView.findViewById(R.id.tv_item_art_location);

        }
    }

    public interface OnItemClickListener {
        void onArtLikeClick(View view, int postion, HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean bean);

        void onItemClick(View view, int position, String service_id);

        void onItemLongClick(View view, int position);
    }

}
