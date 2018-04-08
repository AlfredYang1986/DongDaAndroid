package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class FeaturedThemeAdapter extends RecyclerView.Adapter<FeaturedThemeAdapter.FeaturedViewHolder> {


    List<Integer> list;
    protected Context context;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FeaturedThemeAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_item_featured, null);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeaturedViewHolder holder, int position) {
        /*Uri uri = new Uri.Builder().scheme("res").path(String.valueOf(list.get(position))).build();
        holder.sv_featured.setImageURI(uri);*/

        //        LogUtils.d("xcx",OtherUtils.getUriFromDrawableRes(context,list.get(position)).toString());

        holder.sv_featured.setImageURI(OtherUtils.resourceIdToUri(context, list.get(position)));
        //添加点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getLayoutPosition();
                    listener.onItemClick(holder.itemView, pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_featured;

        public FeaturedViewHolder(View itemView) {
            super(itemView);
            sv_featured = itemView.findViewById(R.id.sv_featured_photo);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

}
