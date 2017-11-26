package com.example.zz.ebuy;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{

    private Context mContext;

    private List<Shop> ShopList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView shopImage;
        TextView shopName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            shopImage = view.findViewById(R.id.shop_image);
            shopName = view.findViewById(R.id.shop_name);
        }
    }

    public ShopAdapter(List<Shop> shopList) {
        ShopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shop shop = ShopList.get(position);
        holder.shopName.setText(shop.getName());
        Glide.with(mContext).load(shop.getImageId()).asBitmap().placeholder(R.drawable.shop).error(R.drawable.shop).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return ShopList.size();
    }

}
