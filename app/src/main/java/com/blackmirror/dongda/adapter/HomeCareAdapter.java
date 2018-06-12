package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean;
import com.blackmirror.dongda.utils.OSSUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeCareAdapter extends RecyclerView.Adapter<HomeCareAdapter.HomeCareViewHolder> {


    private HomepageDomainBean.HomepageServicesBean bean;
    protected Context context;
    private OnCareClickListener listener;


    public void setOnCareClickListener(OnCareClickListener listener) {
        this.listener = listener;
    }

    public HomeCareAdapter(Context context, HomepageDomainBean.HomepageServicesBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public HomeCareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care, parent, false);
        return new HomeCareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeCareViewHolder holder, int position) {

        HomepageDomainBean.HomepageServicesBean.ServicesBean servicesBean = this.bean.getServices().get(position);

        String url = OSSUtils.getSignedUrl(servicesBean.getService_image(), 30 * 60);
        holder.sv_care_photo.setImageURI(url);

        holder.tv_care_name.setText(this.bean.services.get(position).service_tags.get(0));
        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(servicesBean.operation.contains(context.getString(R.string.low_age)) ? context.getString(R.string.low_age) : "")
                .append(servicesBean.service_leaf);
        holder.tv_care_detail.setText(sb.toString());
        holder.tv_care_location.setText(servicesBean.address.substring(0, servicesBean.address.indexOf(context.getString(R.string.str_qu)) + 1));
        initListener(holder, position, servicesBean);

    }

    private void initListener(final HomeCareViewHolder holder, int position, final HomepageDomainBean.HomepageServicesBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareClick(holder.itemView, pos, servicesBean.service_id);
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

    public void setRefreshData(List<HomepageDomainBean.HomepageServicesBean.ServicesBean> list) {
        bean.services.clear();
        bean.services.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnCareClickListener {
        void onItemCareClick(View view, int position, String service_id);
    }

}
