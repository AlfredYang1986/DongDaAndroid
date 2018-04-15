package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.GetOSSClient;
import com.blackmirror.dongda.model.HomeInfoBean;
import com.facebook.drawee.view.SimpleDraweeView;

public class HomeCareAdapter extends RecyclerView.Adapter<HomeCareAdapter.HomeCareViewHolder> {


    HomeInfoBean.ResultBean.HomepageServicesBean bean;
    protected Context context;
    private OnCareClickListener listener;


    public void setOnCareClickListener(OnCareClickListener listener) {
        this.listener = listener;
    }

    public HomeCareAdapter(Context context, HomeInfoBean.ResultBean.HomepageServicesBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public HomeCareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care,parent,false);
        return new HomeCareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeCareViewHolder holder, int position) {

        HomeInfoBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean = this.bean.services.get(position);
        try {
            String url = GetOSSClient.INSTANCE().oss.presignConstrainedObjectURL("bm-dongda", servicesBean.service_image+".jpg", 30 * 60);
            Log.d("xcx", "url: "+url);
            holder.sv_care_photo.setImageURI(url);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        holder.tv_care_name.setText(this.bean.services.get(position).service_tags.get(0));
        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append("的")
                .append(servicesBean.operation.contains("低龄")?"低龄":"")
                .append(servicesBean.service_leaf);
        holder.tv_care_detail.setText(sb.toString());
        holder.tv_care_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf("区")+1));
        initListener(holder,position);

    }

    private void initListener(final HomeCareViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.services == null ? 0 : bean.services.size();
    }

    public static class HomeCareViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_care_photo;
        public TextView tv_care_name;
        public TextView tv_care_detail;
        public TextView tv_care_location;

        public HomeCareViewHolder(View itemView) {
            super(itemView);
            sv_care_photo = itemView.findViewById(R.id.sv_care_photo);
            tv_care_name = itemView.findViewById(R.id.tv_care_name);
            tv_care_detail = itemView.findViewById(R.id.tv_care_detail);
            tv_care_location = itemView.findViewById(R.id.tv_care_location);

        }
    }


    public interface OnCareClickListener {
        void onItemCareClick(View view, int position);
    }

}
