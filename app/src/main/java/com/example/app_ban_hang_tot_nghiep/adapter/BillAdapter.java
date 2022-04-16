package com.example.app_ban_hang_tot_nghiep.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang_tot_nghiep.databinding.ItemBillWaittingBinding;
import com.example.app_ban_hang_tot_nghiep.databinding.ItemListProductCategoryBinding;
import com.example.app_ban_hang_tot_nghiep.model.Product;
import com.example.app_ban_hang_tot_nghiep.model.ResponeBill;
import com.example.app_ban_hang_tot_nghiep.utils.Utils;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ResponeBill mListBill;
    private Context mContext;

    private BillAdapter.onItemCategoryClick onClick;

    public BillAdapter(ResponeBill listBill, Context mContext, BillAdapter.onItemCategoryClick onClick) {
        this.mListBill = listBill;
        this.mContext = mContext;
        this.onClick = onClick;
    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBillWaittingBinding binding = ItemBillWaittingBinding.inflate(inflater, parent, false);

        return new BillAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponeBill data = mListBill;
        Log.d("TAG555", "onBindViewHolder:  " + mListBill.getFeedback() + mListBill.getDate());
        holder.onBind(data);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemBillWaittingBinding mBinding;

        public ViewHolder(ItemBillWaittingBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBind(ResponeBill items) {
            Log.d("TAG555", "onBind: " + items.getDate() + "" + items.getFeedback());
            mBinding.setBillCodes(items.getId() + "");
            mBinding.setTotalMoney(items.getTotal() + "");
            mBinding.setStatus("đang chờ xác nhận");
        }
    }

    public interface onItemCategoryClick {
        public void ItemClick(Product items);
    }
}
