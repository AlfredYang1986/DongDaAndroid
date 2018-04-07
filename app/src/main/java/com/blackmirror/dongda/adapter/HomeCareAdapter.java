package com.blackmirror.dongda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeCareAdapter extends RecyclerView.Adapter<HomeCareAdapter.HomeCareViewHolder> {


    List<Integer> list;
    protected Context context;

    public HomeCareAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HomeCareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_item_care, null);
        return new HomeCareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeCareViewHolder holder, int position) {
        /*Uri uri = new Uri.Builder().scheme("res").path(String.valueOf(list.get(position))).build();
        holder.sv_featured.setImageURI(uri);*/

//        LogUtils.d("xcx",OtherUtils.getUriFromDrawableRes(context,list.get(position)).toString());

        holder.sv_care_photo.setImageURI(OtherUtils.resourceIdToUri(context,list.get(position)));

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class HomeCareViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView sv_care_photo;
        public TextView tv_care_name;
        public TextView tv_care_detail;
        public TextView tv_care_location;

        public HomeCareViewHolder(View itemView) {
            super(itemView);
            sv_care_photo = itemView.findViewById(R.id.sv_care_photo);
            tv_care_name = itemView.findViewById(R.id.tv_care_name);
            tv_care_detail = itemView.findViewById(R.id.tv_care_detail);
            tv_care_location = itemView.findViewById(R.id.tv_care_location);

        }
    }


}