package com.blackmirror.dongda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.HomeExpDomainBean
import com.blackmirror.dongda.utils.dp2px
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeExperienceAdapter(protected var context: Context, internal var bean: HomeExpDomainBean) :
        RecyclerView.Adapter<HomeExperienceAdapter.ExperienceViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_experience, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {

//        displayImage(OtherUtils.resourceIdToUri(context, list!![position]), holder.sv_experience_photo)

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
                .setResizeOptions(ResizeOptions(dp2px(168), dp2px(263)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var sv_experience_photo: SimpleDraweeView
         var tv_exp_day: TextView
         var tv_exp_month: TextView
         var tv_exp_category: TextView
         var tv_exp_title: TextView
         var tv_exp_loc: TextView

        init {
            sv_experience_photo = itemView.findViewById(R.id.sv_experience_photo)
            tv_exp_day = itemView.findViewById(R.id.tv_exp_day)
            tv_exp_month = itemView.findViewById(R.id.tv_exp_month)
            tv_exp_category = itemView.findViewById(R.id.tv_exp_category)
            tv_exp_title = itemView.findViewById(R.id.tv_exp_title)
            tv_exp_loc = itemView.findViewById(R.id.tv_exp_loc)
        }
    }

}
