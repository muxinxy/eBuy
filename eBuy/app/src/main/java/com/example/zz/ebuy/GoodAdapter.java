package com.example.zz.ebuy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder>{

    private Context mContext;

    private List<Good> GoodList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView goodImage;
        TextView goodName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            goodImage = view.findViewById(R.id.good_image);
            goodName = view.findViewById(R.id.good_name);
        }
    }

    public GoodAdapter(List<Good> goodList) {
        GoodList = goodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.good_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Good good = GoodList.get(position);
        holder.goodName.setText(good.getName());
        Glide.with(mContext).load(good.getImageId()).asBitmap().placeholder(R.drawable.good).error(R.drawable.good).into(holder.goodImage);
    }

    @Override
    public int getItemCount() {
        return GoodList.size();
    }

}

