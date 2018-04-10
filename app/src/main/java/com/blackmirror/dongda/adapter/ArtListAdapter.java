package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ArtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Integer> list;
    protected Context context;
    private OnArtListClickListener listener;
    private static final int TYPE_FOOTER = 100;
    private static final int TYPE_NORMAL = 101;


    public void setOnArtListClickListener(OnArtListClickListener listener) {
        this.listener = listener;
    }

    public ArtListAdapter(Context context, List<Integer> list) {
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
            View view = View.inflate(parent.getContext(), R.layout.rv_item_art_list, null);
            return new ArtListViewHolder(view);
        }else {
            View view = View.inflate(parent.getContext(), R.layout.rv_item_footer, null);
            return new ArtFooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ArtListViewHolder) {
            ArtListViewHolder vh = (ArtListViewHolder) holder;
            vh.sv_art_list_photo.setImageURI(OtherUtils.resourceIdToUri(context, list.get(position)));
            initListener(vh, position);
        }else if (holder instanceof ArtFooterViewHolder){

        }

    }

    private void initListener(final ArtListViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemArtListClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size() + 1);
    }

    public static class ArtFooterViewHolder extends RecyclerView.ViewHolder {

        public ArtFooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class ArtListViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_art_list_photo;
        public TextView tv_art_list_name;
        public TextView tv_art_list_content;
        public TextView tv_art_list_location;

        public ArtListViewHolder(View itemView) {
            super(itemView);
            sv_art_list_photo = itemView.findViewById(R.id.sv_art_list_photo);
            tv_art_list_name = itemView.findViewById(R.id.tv_art_list_name);
            tv_art_list_content = itemView.findViewById(R.id.tv_art_list_content);
            tv_art_list_location = itemView.findViewById(R.id.tv_art_list_location);

        }
    }


    public interface OnArtListClickListener {
        void onItemArtListClick(View view, int position);
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
