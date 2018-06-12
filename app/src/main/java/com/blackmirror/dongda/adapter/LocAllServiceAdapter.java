package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class LocAllServiceAdapter extends RecyclerView.Adapter<LocAllServiceAdapter.LocAllServiceHolder> {


    private LocAllServiceDomainBean bean;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public LocAllServiceAdapter(Context context, LocAllServiceDomainBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public LocAllServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_loc_all_service,parent,false);
        return new LocAllServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(LocAllServiceHolder holder, int position) {
        LocAllServiceDomainBean.ServicesBean servicesBean = this.bean.services.get(position);
        StringBuilder sb = new StringBuilder();
        if (servicesBean.service_tags!=null && servicesBean.service_tags.size()!=0 && !TextUtils.isEmpty(servicesBean.service_tags.get(0))){
            sb.append(servicesBean.service_tags.get(0))
                    .append("的");
        }
        sb.append(servicesBean.service_leaf);
        holder.tv_service_loc.setText(sb.toString());
        String url = OSSUtils.getSignedUrl(this.bean.services.get(position).service_image);
        LogUtils.d(url);
        holder.sv_service_photo.setImageURI(url);
        initListener(holder, position, this.bean.services.get(position));

    }

    private void initListener(final LocAllServiceHolder holder, final int position, final LocAllServiceDomainBean.ServicesBean bean) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView,holder.getAdapterPosition(),bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean != null && bean.services != null) {
            return bean.services.size();
        }
        return 0;
    }


    public static class LocAllServiceHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_service_photo;
        public TextView tv_service_loc;

        public LocAllServiceHolder(View itemView) {
            super(itemView);
            sv_service_photo = itemView.findViewById(R.id.sv_service_photo);
            tv_service_loc = itemView.findViewById(R.id.tv_service_loc);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position, LocAllServiceDomainBean.ServicesBean bean);

    }

}
