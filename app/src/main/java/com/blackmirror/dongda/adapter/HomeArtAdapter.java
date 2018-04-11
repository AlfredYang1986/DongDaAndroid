package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.model.HomeInfoBean;
import com.facebook.drawee.view.SimpleDraweeView;

public class HomeArtAdapter extends RecyclerView.Adapter<HomeArtAdapter.HomeArtViewHolder> {


    HomeInfoBean.ResultBean.HomepageServicesBean bean;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeArtAdapter(Context context, HomeInfoBean.ResultBean.HomepageServicesBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public HomeArtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_item_home_art, null);
        return new HomeArtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeArtViewHolder holder, int position) {
        /*Uri uri = new Uri.Builder().scheme("res").path(String.valueOf(list.get(position)))
        .build();
        holder.sv_featured.setImageURI(uri);*/

        //        LogUtils.d("xcx",OtherUtils.getUriFromDrawableRes(context,list.get(position))
        // .toString());

//        holder.sv_item_art_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get(position)));
        HomeInfoBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean = this.bean.services.get(position);
        holder.tv_item_art_name.setText(this.bean.services.get(position).service_tags.get(0));
        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append("的")
                .append(servicesBean.service_leaf)
                .append(servicesBean.category);
        holder.tv_item_art_detail.setText(sb.toString());
        holder.tv_item_art_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf("区")+1));
        initListener(holder, position);

    }

    private void initListener(final HomeArtViewHolder holder, int position) {
        holder.iv_item_art_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onArtLikeClick(holder.iv_item_art_like, pos);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView,holder.getAdapterPosition());
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
        void onArtLikeClick(View view, int postion);

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

}
