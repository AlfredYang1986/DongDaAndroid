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
import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;
import com.blackmirror.dongda.model.uibean.CareMoreUiBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class CareListAdapter extends RecyclerView.Adapter<CareListAdapter.CareListViewHolder> {


    private CareMoreUiBean bean;
    protected Context context;
    private OnCareListClickListener listener;


    public void setOnCareListClickListener(OnCareListClickListener listener) {
        this.listener = listener;
    }

    public CareListAdapter(Context context, CareMoreUiBean bean) {
        this.context = context;
        this.bean = bean;
    }


    @Override
    public CareListAdapter.CareListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care_list,parent,false);
        return new CareListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CareListAdapter.CareListViewHolder holder, int position) {

        CareMoreServerBean.ResultBean.ServicesBean servicesBean = this.bean.services.get(position);

        String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
        holder.sv_care_list_photo.setImageURI(url);
        if (servicesBean.is_collected){
            holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
        }else {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
        }
        holder.tv_care_list_name.setText(servicesBean.service_tags.get(0));
        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append("的")
                .append(servicesBean.operation.contains("低龄")?"低龄":"")
                .append(servicesBean.service_leaf);
        holder.tv_care_list_content.setText(sb.toString());
        holder.tv_care_list_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf("区")+1));
        initListener(holder, position,servicesBean);

    }

    @Override
    public void onBindViewHolder(CareListViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isLike= (boolean) payloads.get(0);
            bean.services.get(position).is_collected= isLike;
            if (isLike){
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final CareListViewHolder holder, int position, final CareMoreServerBean
            .ResultBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareListClick(holder.itemView, pos, servicesBean.service_id);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemCareLikeClick(holder.iv_care_list_like, holder
                            .getAdapterPosition(),servicesBean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return bean.services == null ? 0 : bean.services.size();
    }

    public void setMoreData(List<CareMoreServerBean.ResultBean.ServicesBean> moreList) {
        bean.services.addAll(moreList);
    }

    public void setRefreshData(List<CareMoreServerBean.ResultBean.ServicesBean> moreList) {
        bean.services.clear();
        bean.services.addAll(moreList);
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
        void onItemCareListClick(View view, int position, String service_id);

        void onItemCareLikeClick(View view, int position, CareMoreServerBean.ResultBean
                .ServicesBean servicesBean);
    }

}
