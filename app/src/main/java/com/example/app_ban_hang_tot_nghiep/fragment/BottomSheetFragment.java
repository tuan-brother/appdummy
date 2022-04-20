package com.example.app_ban_hang_tot_nghiep.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.adapter.CartAdapter;
import com.example.app_ban_hang_tot_nghiep.databinding.LayoutDetailBillBinding;
import com.example.app_ban_hang_tot_nghiep.model.ItemCartMoreInfo;
import com.example.app_ban_hang_tot_nghiep.model.ResponeBill;
import com.example.app_ban_hang_tot_nghiep.utils.Utils;
import com.example.app_ban_hang_tot_nghiep.viewmodel.BottomDetailViewModel;
import com.example.app_ban_hang_tot_nghiep.viewmodel.CartViewModel;
import com.example.app_ban_hang_tot_nghiep.viewmodel.MainViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment implements CartAdapter.onItemClick {

    private LayoutDetailBillBinding mBinding;
    private BottomSheetBehavior mSheetBehavior;
    private CartAdapter mAdapter;
    private MainViewModel mViewModel;
    private BottomDetailViewModel mDetailViewModel;
    public List<ItemCartMoreInfo> mListData = new ArrayList<>();
    private ResponeBill dataBill;

    @Override
    public void ItemClick(ItemCartMoreInfo items) {

    }

    @Override
    public void onLongClick(ItemCartMoreInfo items) {

    }

    public static BottomSheetFragment newInstance(ResponeBill data) {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", data);
        bottomSheetFragment.setArguments(bundle);
        return bottomSheetFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        mBinding = LayoutDetailBillBinding.inflate(layoutInflater, null, false);
        dialog.setContentView(mBinding.getRoot());
        mSheetBehavior = BottomSheetBehavior.from((View) mBinding.getRoot().getParent());
        mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        onClick();
        dataBill = (ResponeBill) getArguments().getSerializable("info");
        setUpViewModel();
        setupData();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog sheetDialog = (BottomSheetDialog) dialog;
                setupFullHeight(sheetDialog);
            }
        });

        // Do something with your dialog like setContentView() or whatever
        return dialog;
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        ConstraintLayout bottomSheet = bottomSheetDialog.findViewById(R.id.ctlDetail);
        BottomSheetBehavior behavior;
        if (bottomSheet != null) {
            behavior = BottomSheetBehavior.from((View) bottomSheet.getParent());
            ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
            int windowHeight = getWindowHeight();
            if (layoutParams != null) {
                layoutParams.height = windowHeight;
            }
            bottomSheet.setLayoutParams(layoutParams);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void onClick() {
        mBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setupData() {
        if (mViewModel.listSearch.size() > 0) {
            List<ItemCartMoreInfo> listItem = new ArrayList<>();
            for (int i = 0; i < dataBill.getProducts().size(); i++) {
                Log.d("TAG", "setupData: " + dataBill.getProducts().get(i).getProductId());
                for (int j = 0; j < mViewModel.listSearch.size(); j++) {
                    Log.d("TAG", "search: " + mViewModel.listSearch.get(j).getId());
                    if (dataBill.getProducts().get(i).getProductId().equals(mViewModel.listSearch.get(j).getId())) {
                        ItemCartMoreInfo itemCart = new ItemCartMoreInfo();
                        itemCart.setAmount(dataBill.getProducts().get(i).getAmount());
                        itemCart.setPrice(dataBill.getProducts().get(i).getPrice());
                        itemCart.setImage(mViewModel.listSearch.get(j).getImage().get(0));
                        itemCart.setProductName(dataBill.getProducts().get(i).getProductName());
                        itemCart.setProductId(dataBill.getProducts().get(i).getProductId());
                        listItem.add(itemCart);
                    }
                }
                mListData.clear();
                mListData.addAll(listItem);
                mAdapter = new CartAdapter(mListData, getContext(), this);
                mBinding.recycleOrder.setAdapter(mAdapter);
                mBinding.recycleOrder.getAdapter().notifyDataSetChanged();
                mBinding.tvCodeShow.setText(dataBill.getId());
                mBinding.tvNameCustomer.setText(dataBill.getUsername());
                mBinding.tvStatusBill.setText(dataBill.isBillStatus() + "");
                mBinding.tvMoneyCount.setText(new Utils().convertMoney(dataBill.getTotal()));
            }
        }
    }

    public void setUpViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mDetailViewModel = ViewModelProviders.of(this).get(BottomDetailViewModel.class);
    }

    public void setUpAdapter() {
        mAdapter = new CartAdapter(mListData, getContext(), this);
        mBinding.recycleOrder.setAdapter(mAdapter);
    }
}
