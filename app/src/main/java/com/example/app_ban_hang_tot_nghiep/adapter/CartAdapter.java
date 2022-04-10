package com.example.app_ban_hang_tot_nghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang_tot_nghiep.databinding.ItemHomeProductBinding;
import com.example.app_ban_hang_tot_nghiep.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<Product> mProductList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    private CartAdapter.onItemClick onClick;

    public CartAdapter(List<Product> _student, Context mContext, CartAdapter.onItemClick onClick) {
        this.mProductList = _student;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemHomeProductBinding binding = ItemHomeProductBinding.inflate(inflater, parent, false);

        return new CartAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        Product data = mProductList.get(position);
        holder.onBind(data);
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.ItemClick(data);
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

        ItemHomeProductBinding mBinding;

        public ViewHolder(ItemHomeProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(Product items) {
            mBinding.tvPrices.setText(items.getPrice() + "");
            mBinding.setUrlImage(items.getImage().get(0));
        }
    }

    public interface onItemClick {
        public void ItemClick(Product items);
    }
}
