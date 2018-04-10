package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;

import java.util.List;

public class ArtListTestAdapter extends RecyclerView.Adapter<ArtListTestAdapter.ArtListViewHolder> {


    private List<Integer> list;
    protected Context context;
    private OnArtListClickListener listener;


    public void setOnArtListClickListener(OnArtListClickListener listener) {
        this.listener = listener;
    }

    public ArtListTestAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ArtListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_item_art_list_test, null);
        return new ArtListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtListViewHolder holder, int position) {

        initListener(holder,position);

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
        return list == null ? 0 : list.size();
    }

    public static class ArtListViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_art_list_name;

        public ArtListViewHolder(View itemView) {
            super(itemView);
            tv_art_list_name = itemView.findViewById(R.id.tv_art_list_name);

        }
    }


    public interface OnArtListClickListener {
        void onItemArtListClick(View view, int position);
    }

}
