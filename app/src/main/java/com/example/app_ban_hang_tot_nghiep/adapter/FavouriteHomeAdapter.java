package com.example.app_ban_hang_tot_nghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang_tot_nghiep.databinding.LayoutItemFavoriteBinding;
import com.example.app_ban_hang_tot_nghiep.model.DetailProduct;

import java.util.List;

public class FavouriteHomeAdapter extends RecyclerView.Adapter<FavouriteHomeAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<DetailProduct> mProductList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    private onItemFavouriteClick onClick;

    public FavouriteHomeAdapter(List<DetailProduct> listFavourite, Context mContext, onItemFavouriteClick onClick) {
        this.mProductList = listFavourite;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutItemFavoriteBinding binding = LayoutItemFavoriteBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailProduct data = mProductList.get(position);
        holder.onBind(data);
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onFavouriteClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        LayoutItemFavoriteBinding mBinding;

        public ViewHolder(LayoutItemFavoriteBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(DetailProduct items) {
//            mBinding.tvPrices.setText(new Utils().convertMoney(items.getPrice()));
            if (items.getImage().size() > 0) {
                mBinding.setUrlImage(items.getImage().get(0));
            }
            mBinding.setNameProduct(items.getName());
        }
    }

    public interface onItemFavouriteClick {
        public void onFavouriteClick(DetailProduct items);
    }
}
