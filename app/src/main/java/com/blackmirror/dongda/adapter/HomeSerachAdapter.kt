package com.blackmirror.dongda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.kdomain.model.HomeSearchDomainBean
import com.facebook.drawee.view.SimpleDraweeView

class HomeSerachAdapter(protected var context: Context, private val bean: HomeSearchDomainBean) :
        RecyclerView.Adapter<HomeSerachAdapter.SearchViewHolder>() {

    var totalCount: Int = 0

    private var item: ((View, Int, String?) -> Unit)? = null
    private var like: ((View, Int, CareMoreDomainBean.ServicesBean) -> Unit)? = null

    fun setOnArtListClickListener(it: (View, Int, String?) -> Unit = { view, pos, id -> },
                                  li: (View, Int, CareMoreDomainBean.ServicesBean) -> Unit = { view, pos, sb -> }) {
        item = it
        like = li
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSerachAdapter.SearchViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_search_result, parent, false)
        return SearchViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeSerachAdapter.SearchViewHolder, position: Int) {

        /*if (holder is ArtListAdapter.ArtListViewHolder) {
            val servicesBean = this.bean.services!![position]

            val url = OSSUtils.getSignedUrl(servicesBean.service_image, (30 * 60).toLong())

            holder.sv_art_list_photo.setImageURI(url)
            if (servicesBean.is_collected) {
                holder.iv_item_art_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                holder.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like)
            }
            if (TextUtils.isEmpty(servicesBean.operation!![0])) {
                holder.tv_art_list_name.visibility = View.GONE
            } else {
                holder.tv_art_list_name.visibility = View.VISIBLE
                holder.tv_art_list_name.text = servicesBean.operation!![0]
            }
            val sb = StringBuilder()
            sb.append(servicesBean.brand_name)
                    .append(context.getString(R.string.str_de))
                    .append(if (servicesBean.operation!!.contains(context.getString(R.string.low_age))) context.getString(R.string.low_age) else "")
                    .append(servicesBean.service_leaf)
            holder.tv_art_list_content.text = sb.toString()
            holder.tv_art_list_location.text = servicesBean.address!!.substring(0, servicesBean.address!!.indexOf(context.getString(R.string.str_qu)) + 1)
            initListener(holder, position, servicesBean)
        } else if (holder is ArtListAdapter.ArtFooterViewHolder) {
            *//*LogUtils.d("totalCount "+totalCount+" size "+getItemCount());
            ArtFooterViewHolder vh= (ArtFooterViewHolder) holder;
            if (getItemCount()-1<totalCount){
                vh.cl_art_root.setVisibility(View.GONE);
            }else {
                vh.cl_art_root.setVisibility(View.VISIBLE);
                vh.cl_art_root.setPadding(0, OtherUtils.dp2px(48),0,OtherUtils.dp2px(44));
            }*//*
        }*/

    }

    override fun onBindViewHolder(holder: HomeSerachAdapter.SearchViewHolder, position: Int, payloads: List<Any>?) {

        /*if (payloads!!.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val vh = holder as ArtListAdapter.ArtListViewHolder
            val isLike = payloads[0] as Boolean
            bean.services!![position].is_collected = isLike
            if (isLike) {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.like_selected)
            } else {
                vh.iv_item_art_like.setBackgroundResource(R.drawable.home_art_like)
            }
        }*/
    }

    private fun initListener(holder: ArtListAdapter.ArtListViewHolder, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
        holder.itemView.setOnClickListener {
            item?.invoke(holder.iv_item_art_like, holder.adapterPosition, servicesBean.service_id)
            /*if (listener != null) {
                val pos = holder.adapterPosition
                listener!!.onItemArtListClick(holder.itemView, pos, servicesBean.service_id)
            }*/
        }
        holder.iv_item_art_like.setOnClickListener {
            like?.invoke(holder.iv_item_art_like, holder.adapterPosition, servicesBean)
            /*if (listener != null) {
                val pos = holder.adapterPosition
                listener!!.onItemArtLikeClick(holder.iv_item_art_like, pos, servicesBean)
            }*/
        }
    }

    override fun getItemCount(): Int {
        return 4
    }


    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var sv_search_photo: SimpleDraweeView
        var iv_love: ImageView
        var tv_course_category: TextView
        var tv_age_range: TextView
        var tv_age_unit: TextView
        var tv_course_name: TextView

        init {
            sv_search_photo = itemView.findViewById(R.id.sv_search_photo)
            iv_love = itemView.findViewById(R.id.iv_love)
            tv_course_category = itemView.findViewById(R.id.tv_course_category)
            tv_age_range = itemView.findViewById(R.id.tv_age_range)
            tv_age_unit = itemView.findViewById(R.id.tv_age_unit)
            tv_course_name = itemView.findViewById(R.id.tv_course_name)

        }
    }

    fun setMoreData(moreList: MutableList<CareMoreDomainBean.ServicesBean>?) {

        notifyDataSetChanged()
    }


    fun setRefreshData(list: MutableList<CareMoreDomainBean.ServicesBean>?) {


        notifyDataSetChanged()
    }


}

