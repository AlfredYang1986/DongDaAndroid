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
import com.blackmirror.dongda.kdomain.model.HomeOtherBrandDomainBean
import com.blackmirror.dongda.utils.dp2px
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeOtherBrandAdapter(protected var context: Context, internal var bean: HomeOtherBrandDomainBean) :
        RecyclerView.Adapter<HomeOtherBrandAdapter.OtherBrandViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherBrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_other_brand_course, parent, false)
        return OtherBrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherBrandViewHolder, position: Int) {

//        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_experience_photo)

        //        holder.sv_featured.setImageURI();
        //添加点击事件
        holder.itemView.setOnClickListener {
            item?.invoke(holder.itemView, holder.layoutPosition)
        }

    }

    override fun getItemCount(): Int {
        return 4
    }

    private fun displayImage(uri: Uri, draweeView: SimpleDraweeView) {
        val options = ImageDecodeOptions.newBuilder().setBitmapConfig(Bitmap.Config.RGB_565).build()
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(dp2px(163), dp2px(101)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class OtherBrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_other_photo: SimpleDraweeView
        var iv_other_love: ImageView
        var tv_course_category: TextView
        var tv_age_range: TextView
        var tv_course_name: TextView
        var sv_course_category: SimpleDraweeView
        var tv_course_title: TextView


        init {
            sv_other_photo = itemView.findViewById(R.id.sv_other_photo)
            iv_other_love = itemView.findViewById(R.id.iv_other_love)
            tv_course_category = itemView.findViewById(R.id.tv_course_category)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)
            sv_course_category = itemView.findViewById(R.id.sv_course_category)
            tv_course_title = itemView.findViewById(R.id.tv_course_title)

        }
    }

}
