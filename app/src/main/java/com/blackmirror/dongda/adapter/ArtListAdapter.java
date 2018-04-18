package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.model.serverbean.ArtMoreServerBean;
import com.blackmirror.dongda.model.uibean.ArtMoreUiBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ArtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArtMoreUiBean bean;
    protected Context context;
    private OnArtListClickListener listener;
    private static final int TYPE_FOOTER = 100;
    private static final int TYPE_NORMAL = 101;
    public int totalCount;


    public void setOnArtListClickListener(OnArtListClickListener listener) {
        this.listener = listener;
    }

    public ArtListAdapter(Context context, ArtMoreUiBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getItemViewType(int position) {
        return position == bean.services.size() ? TYPE_FOOTER : TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_art_list, parent, false);
            return new ArtListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_footer, parent, false);
            return new ArtFooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.d("totalCount "+totalCount+" size "+getItemCount());

        if (holder instanceof ArtListViewHolder) {
            ArtListViewHolder vh = (ArtListViewHolder) holder;
            ArtMoreServerBean.ResultBean.ServicesBean servicesBean = this.bean.services.get(position);

            String url = OSSUtils.getSignedUrl(servicesBean.service_image, 30 * 60);
            vh.sv_art_list_photo.setImageURI(url);
            if (servicesBean.is_collected) {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.like_selected);
            } else {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like);
            }
            vh.tv_art_list_name.setText(servicesBean.service_tags.get(0));
            StringBuilder sb = new StringBuilder();
            sb.append(servicesBean.brand_name)
                    .append("的")
                    .append(servicesBean.operation.contains("低龄") ? "低龄" : "")
                    .append(servicesBean.service_leaf);
            vh.tv_art_list_content.setText(sb.toString());
            vh.tv_art_list_location.setText(servicesBean.address.substring(0, servicesBean.address.indexOf("区") + 1));
            initListener(vh, position, servicesBean);
        } else if (holder instanceof ArtFooterViewHolder) {
            /*LogUtils.d("totalCount "+totalCount+" size "+getItemCount());
            ArtFooterViewHolder vh= (ArtFooterViewHolder) holder;
            if (getItemCount()-1<totalCount){
                vh.cl_art_root.setVisibility(View.GONE);
            }else {
                vh.cl_art_root.setVisibility(View.VISIBLE);
                vh.cl_art_root.setPadding(0, OtherUtils.dp2px(48),0,OtherUtils.dp2px(44));
            }*/
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            ArtListViewHolder vh = (ArtListViewHolder) holder;
            boolean isLike= (boolean) payloads.get(0);
            bean.services.get(position).is_collected= isLike;
            if (isLike){
                vh.iv_item_art_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final ArtListViewHolder holder, int position, final ArtMoreServerBean.ResultBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemArtListClick(holder.itemView, pos, servicesBean.service_id);
                }
            }
        });
        holder.iv_item_art_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemArtLikeClick(holder.iv_item_art_like, pos, servicesBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.services == null ? 0 : (bean.services.size() + 1);
    }

    public static class ArtFooterViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout cl_art_root;
        public ArtFooterViewHolder(View itemView) {
            super(itemView);
            cl_art_root=itemView.findViewById(R.id.cl_art_root);
        }
    }


    public static class ArtListViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_art_list_photo;
        public ImageView iv_item_art_like;
        public TextView tv_art_list_name;
        public TextView tv_art_list_content;
        public TextView tv_art_list_location;

        public ArtListViewHolder(View itemView) {
            super(itemView);
            sv_art_list_photo = itemView.findViewById(R.id.sv_art_list_photo);
            iv_item_art_like = itemView.findViewById(R.id.iv_item_art_like);
            tv_art_list_name = itemView.findViewById(R.id.tv_art_list_name);
            tv_art_list_content = itemView.findViewById(R.id.tv_art_list_content);
            tv_art_list_location = itemView.findViewById(R.id.tv_art_list_location);

        }
    }

    public void setMoreData(List<ArtMoreServerBean.ResultBean.ServicesBean> moreList) {
        bean.services.addAll(moreList);
        notifyDataSetChanged();
    }


    public void setRefreshData(List<ArtMoreServerBean.ResultBean.ServicesBean> list) {
        bean.services.clear();
        bean.services.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnArtListClickListener {
        void onItemArtListClick(View view, int position, String service_id);

        void onItemArtLikeClick(View view, int position, ArtMoreServerBean.ResultBean.ServicesBean servicesBean);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }
}

