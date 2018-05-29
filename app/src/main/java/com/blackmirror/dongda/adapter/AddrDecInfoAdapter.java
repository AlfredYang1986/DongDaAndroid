package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.model.SafeUiBean;

import java.util.List;

public class AddrDecInfoAdapter extends RecyclerView.Adapter<AddrDecInfoAdapter.AddrInfoViewHolder> {


    protected Context context;
    private OnCareClickListener listener;
    private List<SafeUiBean> list;


    public void setOnCareClickListener(OnCareClickListener listener) {
        this.listener = listener;
    }

    public AddrDecInfoAdapter(Context context, List<SafeUiBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AddrInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //解决Recyclerview宽高失效的问题
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_addr_dec,parent,false);
//        View view = View.inflate(parent.getContext(), R.layout.rv_item_addr_dec, null);
        return new AddrInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddrInfoViewHolder holder, int position) {

        holder.iv_item_addr_photo.setBackgroundResource(list.get(position).res_id);
        holder.tv_item_addr_dec.setText(list.get(position).dec);
//        initListener(holder,position);

    }

    private void initListener(final AddrInfoViewHolder holder, int position) {
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
        return list == null ? 0 : list.size();
    }

    public static class AddrInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_item_addr_photo;
        public TextView tv_item_addr_dec;


        public AddrInfoViewHolder(View itemView) {
            super(itemView);
            iv_item_addr_photo=itemView.findViewById(R.id.iv_item_addr_photo);
            tv_item_addr_dec=itemView.findViewById(R.id.tv_item_addr_dec);
        }
    }


    public interface OnCareClickListener {
        void onItemCareClick(View view, int position);
    }

}
