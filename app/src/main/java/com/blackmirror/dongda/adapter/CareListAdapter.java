package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CareListAdapter extends RecyclerView.Adapter<CareListAdapter.CareListViewHolder> {


    private CareMoreDomainBean bean;
    protected Context context;
    private OnCareListClickListener listener;
    public Set<String> urlSet=new HashSet<>();



    public void setOnCareListClickListener(OnCareListClickListener listener) {
        this.listener = listener;
    }

    public CareListAdapter(Context context, CareMoreDomainBean bean) {
        this.context = context;
        this.bean = bean;
    }


    @Override
    public CareListAdapter.CareListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_care_list,parent,false);
        return new CareListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CareListAdapter.CareListViewHolder holder, int position) {

        CareMoreDomainBean.ServicesBean servicesBean = this.bean.services.get(position);


        String url= OSSUtils.getSignedUrl(servicesBean.service_image,30*60);
//        holder.sv_care_list_photo.setImageURI(url);
        urlSet.add(getCacheUrl(url));


        displayImage(Uri.parse(url),holder.sv_care_list_photo);

        if (servicesBean.is_collected){
            holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
        }else {
            holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
        }
        holder.tv_care_list_name.setText(servicesBean.service_tags.get(0));
        StringBuilder sb = new StringBuilder();
        sb.append(servicesBean.brand_name)
                .append(context.getString(R.string.str_de))
                .append(servicesBean.operation.contains(context.getString(R.string.low_age))?context.getString(R.string.low_age):"")
                .append(servicesBean.service_leaf);
        holder.tv_care_list_content.setText(sb.toString());
        holder.tv_care_list_location.setText(servicesBean.address.substring(0,servicesBean.address.indexOf(context.getString(R.string.str_qu))+1));
        initListener(holder, position,servicesBean);

    }

    @Override
    public void onBindViewHolder(CareListViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            boolean isLike= (boolean) payloads.get(0);
            bean.services.get(position).is_collected= isLike;
            if (isLike){
                holder.iv_care_list_like.setBackgroundResource(R.drawable.like_selected);
            }else {
                holder.iv_care_list_like.setBackgroundResource(R.drawable.home_art_like);
            }
        }
    }

    private void initListener(final CareListViewHolder holder, int position, final CareMoreDomainBean.ServicesBean servicesBean) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemCareListClick(holder.itemView, pos, servicesBean.service_id);
                }
            }
        });
        holder.iv_care_list_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemCareLikeClick(holder.iv_care_list_like, holder
                            .getAdapterPosition(),servicesBean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return bean.services == null ? 0 : bean.services.size();
    }

    public void setMoreData(List<CareMoreDomainBean.ServicesBean> moreList) {
        bean.services.addAll(moreList);
        notifyDataSetChanged();
    }

    public void setRefreshData(List<CareMoreDomainBean.ServicesBean> moreList) {
        bean.services.clear();
        bean.services.addAll(moreList);
        notifyDataSetChanged();
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
        void onItemCareListClick(View view, int position, String service_id);

        void onItemCareLikeClick(View view, int position, CareMoreDomainBean.ServicesBean servicesBean);
    }

    public void displayImage(Uri uri, SimpleDraweeView draweeView){

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(DensityUtils.getScreenWidthDp()-32), DensityUtils.dp2px(212)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    protected String getCacheUrl(String url){
        if (url.contains("?")){
            return url.substring(0,url.indexOf("?")+1);
        }
        return url;

    }

}
