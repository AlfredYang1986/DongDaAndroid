package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeArtAdapter extends RecyclerView.Adapter<HomeArtAdapter.HomeArtViewHolder> {


    List<Integer> list;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HomeArtAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
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

        holder.sv_item_art_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get
                (position)));
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
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
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
