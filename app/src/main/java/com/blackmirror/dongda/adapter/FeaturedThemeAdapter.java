package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.OtherUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class FeaturedThemeAdapter extends RecyclerView.Adapter<FeaturedThemeAdapter.FeaturedViewHolder> {


    List<Integer> list;
    protected Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FeaturedThemeAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_featured,parent,false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeaturedViewHolder holder, int position) {

        displayImage(OtherUtils.resourceIdToUri(context, list.get(position)),holder.sv_featured);

//        holder.sv_featured.setImageURI();
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

    public void displayImage(Uri uri, SimpleDraweeView draweeView){
        ImageDecodeOptions options = ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(235), DensityUtils.dp2px(311)))
                .setImageDecodeOptions(options)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
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
