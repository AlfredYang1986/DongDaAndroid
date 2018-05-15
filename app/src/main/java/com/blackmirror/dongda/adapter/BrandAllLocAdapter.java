package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;

public class BrandAllLocAdapter extends RecyclerView.Adapter<BrandAllLocAdapter.BrandAllLocHolder> {


    private BrandAllLocDomainBean bean;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BrandAllLocAdapter(Context context, BrandAllLocDomainBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public BrandAllLocHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_brand_all_loc,parent,false);
        return new BrandAllLocHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandAllLocHolder holder, int position) {
        holder.tv_brand_loc.setText(bean.locations.get(position).address);
        initListener(holder, position,bean);

    }

    private void initListener(final BrandAllLocHolder holder, final int position, final BrandAllLocDomainBean servicesBean) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
//                    listener.onItemClick(holder.itemView,holder.getAdapterPosition(),servicesBean.service_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean.locations != null) {
            return bean.locations.size();
        }
        return 0;
    }


    public static class BrandAllLocHolder extends RecyclerView.ViewHolder {

        public TextView tv_brand_loc;
        public TextView tv_brand_loc_detail;

        public BrandAllLocHolder(View itemView) {
            super(itemView);
            tv_brand_loc = itemView.findViewById(R.id.tv_brand_loc);
            tv_brand_loc_detail = itemView.findViewById(R.id.tv_brand_loc_detail);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position, String service_id);

    }

}
