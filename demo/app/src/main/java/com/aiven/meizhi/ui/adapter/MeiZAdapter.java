package com.aiven.meizhi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aiven.meizhi.R;
import com.aiven.meizhi.model.ImgBase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/23  14:28
 * Desc :
 */
public class MeiZAdapter extends RecyclerView.Adapter<MeiZAdapter.HolderView> {

    private List<ImgBase> dataList;
    private Context mContext;
    private OnTapClickListener mListener;

    public MeiZAdapter(List<ImgBase> list, Context ctx, OnTapClickListener listener) {
        this.dataList = list;
        this.mContext = ctx;
        this.mListener = listener;
    }

    public void update(List<ImgBase> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        ImgBase imgBase = dataList.get(position);
        holder.base = imgBase;
        Picasso.with(mContext).load(imgBase.getThumbImgUrl()).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        CardView card;
        ImageView imgView;
        ImgBase base;

        public HolderView(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            imgView = (ImageView) itemView.findViewById(R.id.iv_meizhi);
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onClick(base, view);
                    }
                }
            });
        }
    }

    public interface OnTapClickListener {
        public void onClick(ImgBase base, View view);
    }
}
