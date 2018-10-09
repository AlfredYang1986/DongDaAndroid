package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.VideoListDomainBean
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.ToastUtils
import com.facebook.drawee.view.SimpleDraweeView

class VideoListAdapter(protected var context: Context, private val bean: VideoListDomainBean) : RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder>() {

    var totalCount: Int = 0

    private var item: ((View, Int, String?) -> Unit)? = null
//    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnVideoItemClickListener(it: (View, Int, String?) -> Unit = { view, pos, id -> }) {
        item = it
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListAdapter.VideoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_video_list, parent, false)
        return VideoListViewHolder(view)

    }

    override fun onBindViewHolder(holder: VideoListAdapter.VideoListViewHolder, position: Int) {


//        val url = OSSUtils.getSignedUrl(bean.imgList[position], (30 * 60).toLong())

//        holder.sv_video_list_photo.setImageURI(url)

        initListener(holder, position, bean.videoList[position])


    }

    override fun onBindViewHolder(holder: VideoListAdapter.VideoListViewHolder, position: Int, payloads: List<Any>?) {

        if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val isLike = payloads[0] as Boolean
        }
    }

    private fun initListener(holder: VideoListViewHolder, position: Int, url: String) {
        holder.itemView.setOnClickListener {
            LogUtils.d("video click")
            ToastUtils.showShortToast("zzzzz")
            item?.invoke(holder.itemView,holder.adapterPosition,url)
        }
    }

    override fun getItemCount(): Int {
        return bean.videoList.size
    }


    class VideoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_video_list_photo: SimpleDraweeView
        var tv_video_list_name: TextView
        var tv_video_list_content: TextView
        var tv_video_list_location: TextView

        init {
            sv_video_list_photo = itemView.findViewById(R.id.sv_video_list_photo)
            tv_video_list_name = itemView.findViewById(R.id.tv_video_list_name)
            tv_video_list_content = itemView.findViewById(R.id.tv_video_list_content)
            tv_video_list_location = itemView.findViewById(R.id.tv_video_list_location)

        }
    }

    /*fun setMoreData(moreList: MutableList<CareMoreDomainBean.ServicesBean>?) {
        moreList?.apply {
            bean.services?.addAll(this)
        }
        notifyDataSetChanged()
    }*/


    /*fun setRefreshData(list: MutableList<CareMoreDomainBean.ServicesBean>?) {

        bean.services?.let {
            it.clear()
            list
        }?.apply {
            bean.services?.addAll(this)
        }

        notifyDataSetChanged()
    }*/

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
//        val layoutManager = recyclerView!!.layoutManager
//
//        if (layoutManager is GridLayoutManager) {
//            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return if (getItemViewType(position) == TYPE_FOOTER) {
//                        layoutManager.spanCount
//                    } else {
//                        1
//                    }
//                }
//            }
//        }
    }
}

