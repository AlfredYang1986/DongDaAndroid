package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.model.TestFeaturedDetailBean;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Ruge on 2018-04-08 下午4:12
 */
public class FeaturedDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> list;
    protected Context context;
    public int title;

    public TestFeaturedDetailBean bean;

    public FeaturedDetailAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    private static final int HEAD_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private static final int FOOT_TYPE = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE){
            View view = View.inflate(parent.getContext(), R.layout.rv_item_featured_detail_head,null);
            return new HeadViewHolder(view);
        }else if (viewType==NORMAL_TYPE){
            View view = View.inflate(parent.getContext(), R.layout.rv_item_featured_detail_normal,null);
            return new NormalViewHolder(view);
        }else {
            View view = View.inflate(parent.getContext(), R.layout.rv_item_featured_detail_footer,null);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){
            HeadViewHolder vh= (HeadViewHolder) holder;
            vh.iv_featured_detail_bg.setBackgroundResource(bean.bg_redId);
            vh.tv_featured_detail_content.setText(bean.title);
            vh.tv_item_head_content.setText(bean.content);
        }
        if (holder instanceof NormalViewHolder){
            //设置圆角
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadii(OtherUtils.dp2px(4), OtherUtils.dp2px(4), 0, 0);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            NormalViewHolder vh = (NormalViewHolder) holder;
            vh.sv_featured_detail_photo.setHierarchy(hierarchy);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD_TYPE;
        }else if (position == list.size()+1){
            return FOOT_TYPE;
        }else {
            return NORMAL_TYPE;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_featured_detail_bg;
        public TextView tv_featured_detail_content;
        public TextView tv_item_head_content;

        public HeadViewHolder(View itemView) {
            super(itemView);
            iv_featured_detail_bg = itemView.findViewById(R.id.iv_featured_detail_bg);
            tv_featured_detail_content = itemView.findViewById(R.id.tv_featured_detail_content);
            tv_item_head_content = itemView.findViewById(R.id.tv_item_head_content);
        }
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_featured_detail_photo;
        public TextView tv_featured_detail_content;
        public TextView tv_featured_detail_type;
        public TextView tv_featured_detail_location;

        public NormalViewHolder(View itemView) {
            super(itemView);
            sv_featured_detail_photo = itemView.findViewById(R.id.sv_featured_detail_photo);
            tv_featured_detail_content = itemView.findViewById(R.id.tv_featured_detail_content);
            tv_featured_detail_type = itemView.findViewById(R.id.tv_featured_detail_type);
            tv_featured_detail_location = itemView.findViewById(R.id.tv_featured_detail_location);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_featured_detail_end;

        public FootViewHolder(View itemView) {
            super(itemView);
            iv_featured_detail_end = itemView.findViewById(R.id.iv_featured_detail_end);
        }
    }

}
