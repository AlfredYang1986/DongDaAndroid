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
import com.blackmirror.dongda.kdomain.model.HomeGrowthDomainBean
import com.blackmirror.dongda.ui.view.TimeLineView
import com.blackmirror.dongda.utils.DensityUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class HomeGrowthAdapter(protected var context: Context, protected var bean: HomeGrowthDomainBean) :
        RecyclerView.Adapter<HomeGrowthAdapter.GrowthViewHolder>() {

    private var item: ((View, Int) -> Unit)? = null

    fun setOnItemClickListener(l: ((View, Int) -> Unit)? = null) {
        item = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrowthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_home_growth, parent, false)
        return GrowthViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrowthViewHolder, position: Int) {

        if (holder.adapterPosition == 0) {
            holder.tlv_pl.setFristItem()
        }
        if (holder.adapterPosition == itemCount - 1) {
            holder.tlv_pl.setLastItem()
        }

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
                .setResizeOptions(ResizeOptions(DensityUtils.dp2px(168), DensityUtils.dp2px(263)))
                .setImageDecodeOptions(options)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }


    class GrowthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_day: TextView
        var tv_month: TextView
        var tlv_pl: TimeLineView
        var tv_time: TextView
        var tv_dec: TextView
        var tv_title: TextView
        var sv_photo: SimpleDraweeView

        init {
            tv_day = itemView.findViewById(R.id.tv_day)
            tv_month = itemView.findViewById(R.id.tv_month)
            tlv_pl = itemView.findViewById(R.id.tlv_pl)
            tv_time = itemView.findViewById(R.id.tv_time)
            tv_dec = itemView.findViewById(R.id.tv_dec)
            tv_title = itemView.findViewById(R.id.tv_title)
            sv_photo = itemView.findViewById(R.id.sv_photo)


        }
    }

}
