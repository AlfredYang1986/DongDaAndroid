package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyLikeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int minCount = 10;
    private final int totalCount = 301;
    public boolean canLoadMore;
    public boolean load_complete;
    private List<Integer> list;
    protected Context context;
    private OnCareListClickListener listener;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;

    public void setOnCareListClickListener(OnCareListClickListener listener) {
        this.listener = listener;
    }

    public MyLikeListAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == list.size() ? TYPE_FOOTER : TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = View.inflate(parent.getContext(), R.layout.rv_item_care_list, null);
            return new CareListViewHolder(view);
        } else {
            View view = View.inflate(parent.getContext(), R.layout.rv_item_refresh_footer, null);
            return new FooterViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CareListViewHolder) {
            CareListViewHolder vh = (CareListViewHolder) holder;
            vh.sv_care_list_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get(position)));
            initListener(vh, position);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder vh= (FooterViewHolder) holder;
            if (!canLoadMore){
                vh.cpb_load_more.setVisibility(View.INVISIBLE);
                vh.tv_load_more.setText("没有更多数据了");
            }
            if (load_complete){
                load_complete=false;
                vh.cpb_load_more.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (holder instanceof FooterViewHolder) {
                String str = (String) payloads.get(0);
                FooterViewHolder vh= (FooterViewHolder) holder;
                if (canLoadMore){
                    if (str.equals("visible")){
                        vh.itemView.setVisibility(View.VISIBLE);
                        vh.cpb_load_more.setVisibility(View.VISIBLE);
                    }else {
                        vh.itemView.setVisibility(View.VISIBLE);
                        vh.cpb_load_more.setVisibility(View.INVISIBLE);
                    }
                }else {
                    vh.cpb_load_more.setVisibility(View.INVISIBLE);
                    vh.tv_load_more.setText("没有更多数据了");
                }

            }
        }
    }

    private void initListener(final CareListViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareListClick(holder.itemView, pos);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemCareLikeClick(holder.iv_care_list_like, holder.getAdapterPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    public void setMoreData(List<Integer> moreList) {
        list.addAll(moreList);
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout cl_foot_root;
        public ContentLoadingProgressBar cpb_load_more;
        public TextView tv_load_more;


        public FooterViewHolder(View itemView) {
            super(itemView);
            cl_foot_root = itemView.findViewById(R.id.cl_foot_root);
            cpb_load_more = itemView.findViewById(R.id.cpb_load_more);
            tv_load_more = itemView.findViewById(R.id.tv_load_more);
        }
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
        void onItemCareListClick(View view, int position);

        void onItemCareLikeClick(View view, int position);
    }

}
