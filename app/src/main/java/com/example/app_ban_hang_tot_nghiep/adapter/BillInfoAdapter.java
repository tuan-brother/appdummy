package com.example.app_ban_hang_tot_nghiep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang_tot_nghiep.databinding.ItemListBillInfoBinding;
import com.example.app_ban_hang_tot_nghiep.databinding.ItemListCartBinding;
import com.example.app_ban_hang_tot_nghiep.model.ItemProductCart;
import com.example.app_ban_hang_tot_nghiep.utils.Utils;

import java.util.List;

public class BillInfoAdapter extends RecyclerView.Adapter<BillInfoAdapter.ViewHolder> {

    private List<ItemProductCart> mCartList;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    private BillInfoAdapter.onItemClick onClick;

    public BillInfoAdapter(List<ItemProductCart> mCart, Context mContext, BillInfoAdapter.onItemClick onClick) {
        this.mCartList = mCart;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @Override
    public BillInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemListBillInfoBinding binding = ItemListBillInfoBinding.inflate(inflater, parent, false);

        return new BillInfoAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(BillInfoAdapter.ViewHolder holder, int position) {
        ItemProductCart data = mCartList.get(position);
        holder.onBind(data);
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.ItemClick(data);
            }
        });
        holder.mBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClick.onLongClick(data);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemListBillInfoBinding mBinding;

        public ViewHolder(ItemListBillInfoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(ItemProductCart items) {
            mBinding.setPrices(new Utils().convertMoney(items.getPrice()));
            mBinding.setTitle(items.getProductName());
            mBinding.setDes(items.getAmount() + "");
            if(items.getImage().size() > 0){
                mBinding.setUrlImage(items.getImage().get(0));
            }
        }
    }

    public interface onItemClick {
        public void ItemClick(ItemProductCart items);

        public void onLongClick(ItemProductCart items);
    }
}
