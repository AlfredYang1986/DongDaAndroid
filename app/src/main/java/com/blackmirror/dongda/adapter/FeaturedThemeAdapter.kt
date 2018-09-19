package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.blackmirror.dongda.utils.DensityUtils
import com.blackmirror.dongda.utils.OtherUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class FeaturedThemeAdapter(protected var context: Context, internal var list: List<Int>?) : RecyclerView.Adapter<FeaturedThemeAdapter.FeaturedViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_featured, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {

        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_featured)

        //        holder.sv_featured.setImageURI();
        //添加点击事件
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.layoutPosition)
        }

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {
        val options = ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build()
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(DensityUtils.dp2px(235), DensityUtils.dp2px(311)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_featured: SimpleDraweeView

        init {
            sv_featured = itemView.findViewById(R.id.sv_featured_photo)
        }
    }

}
