package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomeHotRecommendDomainBean
import com.blackmirror.dongda.utils.DensityUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeHotRecommendAdapter(protected var context: Context, internal var bean: HomeHotRecommendDomainBean) :
        RecyclerView.Adapter<HomeHotRecommendAdapter.RecommendViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_hot_recommend, parent, false)
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {

//        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_featured)

        //        holder.sv_featured.setImageURI();
        //添加点击事件
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.layoutPosition)
        }

    }

    override fun getItemCount(): Int {
        return 6
    }

    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {
        val options = ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build()
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(DensityUtils.dp2px(191), DensityUtils.dp2px(118)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class RecommendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_recommend_photo: SimpleDraweeView
        var iv_recommend_love: ImageView
        var tv_brand_name: TextView
        var tv_age_range: TextView
        var tv_course_name: TextView

        init {
            sv_recommend_photo = itemView.findViewById(R.id.sv_recommend_photo)
            iv_recommend_love = itemView.findViewById(R.id.iv_recommend_love)
            tv_brand_name = itemView.findViewById(R.id.tv_brand_name)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)
        }
    }

}
