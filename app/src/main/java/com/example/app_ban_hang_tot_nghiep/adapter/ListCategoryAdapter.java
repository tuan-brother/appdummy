package com.example.app_ban_hang_tot_nghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.databinding.ItemCategoryBinding;
import com.example.app_ban_hang_tot_nghiep.databinding.ItemHomeProductBinding;
import com.example.app_ban_hang_tot_nghiep.model.DetailProduct;
import com.example.app_ban_hang_tot_nghiep.model.Product;

import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<DetailProduct> mProductList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;
    int selectItem = 0;

    private onDetailItemClick onClick;

    public ListCategoryAdapter(List<DetailProduct> mListDetail, Context mContext, onDetailItemClick onClick) {
        this.mProductList = mListDetail;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailProduct data = mProductList.get(position);
        holder.onBind(data);
        if (selectItem == position) {
            holder.mBinding.getRoot().setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_corner_20_color_neutral_8_selected));
        } else {
            holder.mBinding.getRoot().setBackground(mContext.getResources().getDrawable(R.drawable.bg_view_corner_20_color_neutral_8));
        }
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.ItemClick(data, position);
                notifyItemChanged(selectItem);
                selectItem = position;
                notifyItemChanged(selectItem);
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

        ItemCategoryBinding mBinding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(DetailProduct items) {
            mBinding.setName(items.getSize().toString());
        }
    }

    public interface onDetailItemClick {
        public void ItemClick(DetailProduct items, Integer position);
    }
}
