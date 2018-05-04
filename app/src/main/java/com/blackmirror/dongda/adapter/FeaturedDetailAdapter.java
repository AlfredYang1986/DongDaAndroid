package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by Ruge on 2018-04-08 下午4:12
 */
public class FeaturedDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CareMoreServerBean.ResultBean.ServicesBean> list;
    protected Context context;
    public String title;
    public String content;
    public int bg_resId;

    private static final int HEAD_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private static final int FOOT_TYPE = 3;

    private OnDetailListClickListener listener;



    public void setOnDetailListClickListener(OnDetailListClickListener listener) {
        this.listener = listener;
    }

    public FeaturedDetailAdapter(Context context, List<CareMoreServerBean.ResultBean.ServicesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_featured_detail_head,parent,false);
            return new HeadViewHolder(view);
        }else if (viewType==NORMAL_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_featured_detail_normal,parent,false);
            return new NormalViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_featured_detail_footer,parent,false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){
            HeadViewHolder vh= (HeadViewHolder) holder;
            vh.iv_featured_detail_bg.setBackgroundResource(bg_resId);
            vh.tv_featured_detail_content.setText(title);
            vh.tv_item_head_content.setText(content);
        }
        if (holder instanceof NormalViewHolder){
            NormalViewHolder vh = (NormalViewHolder) holder;
           /* //设置圆角
            RoundingParams roundingParams = new RoundingParams();
            roundingParams.setCornersRadii(OtherUtils.dp2px(4), OtherUtils.dp2px(4), 0, 0);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setRoundingParams(roundingParams);
            vh.sv_featured_detail_photo.setHierarchy(hierarchy);*/

            CareMoreServerBean.ResultBean.ServicesBean servicesBean = list.get(position-1);


            String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
            //        holder.sv_care_list_photo.setImageURI(url);
//            urlSet.add(getCacheUrl(url));


            displayImage(Uri.parse(url),vh.sv_featured_detail_photo);

            if (servicesBean.is_collected){
                vh.iv_featured_detail_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                vh.iv_featured_detail_like.setBackgroundResource(R.drawable.home_art_like);
            }

            if (servicesBean.service_type.contains(context.getString(R.string.str_care))){
                vh.tv_featured_detail_type.setText(servicesBean.service_leaf);
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(servicesBean.service_type)
                        .append("·")
                        .append(servicesBean.service_leaf)
                        .append(servicesBean.category);
                vh.tv_featured_detail_type.setText(sb.toString());
            }

            StringBuilder sb = new StringBuilder();
            sb.append(servicesBean.brand_name)
                    .append("\"")
                    .append(servicesBean.punchline)
                    .append("\"");
            vh.tv_featured_detail_content.setText(sb.toString());
            vh.tv_featured_detail_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf(context.getString(R.string.str_qu))+1));
            initListener(vh, position,servicesBean);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else if (holder instanceof NormalViewHolder){
            NormalViewHolder vh = (NormalViewHolder) holder;
            boolean isLike= (boolean) payloads.get(0);
            list.get(position-1).is_collected= isLike;
            if (isLike){
                vh.iv_featured_detail_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                vh.iv_featured_detail_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final NormalViewHolder holder, int position, final CareMoreServerBean.ResultBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemDetailListClick(holder.itemView, pos, servicesBean.service_id);
                }
            }
        });
        holder.fl_featured_detail_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemDetailLikeClick(holder.fl_featured_detail_like, holder
                            .getAdapterPosition(),servicesBean);
                }
            }
        });
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

    public void displayImage(Uri uri, SimpleDraweeView draweeView){

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(DensityUtils.getScreenWidthDp()-32), DensityUtils.dp2px(211)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
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

        public CardView cv_root;
        public SimpleDraweeView sv_featured_detail_photo;
        public ImageView iv_featured_detail_like;
        public FrameLayout fl_featured_detail_like;
        public TextView tv_featured_detail_content;
        public TextView tv_featured_detail_type;
        public TextView tv_featured_detail_location;

        public NormalViewHolder(View itemView) {
            super(itemView);
            cv_root = itemView.findViewById(R.id.cv_root);
            sv_featured_detail_photo = itemView.findViewById(R.id.sv_featured_detail_photo);
            fl_featured_detail_like = itemView.findViewById(R.id.fl_featured_detail_like);
            iv_featured_detail_like = itemView.findViewById(R.id.iv_featured_detail_like);
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

    public interface OnDetailListClickListener {
        void onItemDetailListClick(View view, int position, String service_id);

        void onItemDetailLikeClick(View view, int position, CareMoreServerBean.ResultBean
                .ServicesBean servicesBean);
    }

}
