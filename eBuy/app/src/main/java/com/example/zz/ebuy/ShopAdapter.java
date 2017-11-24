package com.example.zz.ebuy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        View shopView;
        CardView cardView;
        ImageView shopImage;
        TextView shopName;

        ViewHolder(View view) {
            super(view);
            shopView=view;
            cardView = (CardView) view;
            shopImage = view.findViewById(R.id.shop_image);
            shopName = view.findViewById(R.id.shop_name);
        }
    }

    ShopAdapter(List<Shop> shopList) {
        ShopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Shop shop=ShopList.get(position);
                Intent intent=new Intent(v.getContext(),Add.class);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shop shop = ShopList.get(position);
        holder.shopName.setText(shop.getName());
        Glide.with(mContext).load(shop.getImageId()).asBitmap().error(R.drawable.shop0).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return ShopList.size();
    }

}
