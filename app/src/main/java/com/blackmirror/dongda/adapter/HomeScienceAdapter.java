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
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.model.serverbean.HomeInfoServerBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeScienceAdapter extends RecyclerView.Adapter<HomeScienceAdapter.HomeScienceViewHolder> {


    HomeInfoServerBean.ResultBean.HomepageServicesBean bean;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeScienceAdapter(Context context,  HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public HomeScienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_home_science,parent,false);
        return new HomeScienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeScienceViewHolder holder, int position) {


        HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean = this.bean.services.get(position);

        String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
        holder.sv_item_science_photo.setImageURI(url);

        /*try {
            String url = GetOSSClient.INSTANCE().oss.presignConstrainedObjectURL("bm-dongda", servicesBean.service_image+".jpg", 30 * 60);
            Log.d("xcx", "url: "+url);
            holder.sv_item_science_photo.setImageURI(url);
        } catch (ClientException e) {
            e.printStackTrace();
        }*/

        if (servicesBean.is_collected){
            holder.iv_item_science_like.setBackgroundResource(R.drawable.like_selected);
        }else {
            holder.iv_item_science_like.setBackgroundResource(R.drawable.home_art_like);
        }

        if (TextUtils.isEmpty(bean.services.get(position).service_tags.get(0))){
            holder.tv_item_science_name.setVisibility(View.GONE);
        }else {
            holder.tv_item_science_name.setVisibility(View.VISIBLE);
            holder.tv_item_science_name.setText(bean.services.get(position).service_tags.get(0));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append("的")
                .append(servicesBean.service_leaf)
                .append(servicesBean.category);
        holder.tv_item_science_detail.setText(sb.toString());
        holder.tv_item_science_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf("区")+1));

        initListener(holder, position, servicesBean);

    }

    @Override
    public void onBindViewHolder(HomeScienceViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isLike= (boolean) payloads.get(0);
            bean.services.get(position).is_collected= isLike;
            if (isLike){
                holder.iv_item_science_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                holder.iv_item_science_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final HomeScienceViewHolder holder, int position, final HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onScienceItemClick(holder.iv_item_science_like, pos, servicesBean.service_id);
                }
            }
        });

        holder.iv_item_science_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onScienceLikeClick(holder.iv_item_science_like, pos, servicesBean);
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

    public static class HomeScienceViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_item_science_photo;
        public ImageView iv_item_science_like;
        public TextView tv_item_science_name;
        public TextView tv_item_science_detail;
        public TextView tv_item_science_location;

        public HomeScienceViewHolder(View itemView) {
            super(itemView);
            sv_item_science_photo = itemView.findViewById(R.id.sv_item_science_photo);
            iv_item_science_like = itemView.findViewById(R.id.iv_item_science_like);
            tv_item_science_name = itemView.findViewById(R.id.tv_item_science_name);
            tv_item_science_detail = itemView.findViewById(R.id.tv_item_science_detail);
            tv_item_science_location = itemView.findViewById(R.id.tv_item_science_location);

        }
    }

    public interface OnItemClickListener {
        void onScienceLikeClick(View view, int position, HomeInfoServerBean.ResultBean
                .HomepageServicesBean.ServicesBean servicesBean);

        void onScienceItemClick(View view, int position, String service_id);

    }

}
