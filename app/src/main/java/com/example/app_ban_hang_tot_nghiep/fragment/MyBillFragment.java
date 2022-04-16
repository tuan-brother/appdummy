package com.example.app_ban_hang_tot_nghiep.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.adapter.BillAdapter;
import com.example.app_ban_hang_tot_nghiep.adapter.CartAdapter;
import com.example.app_ban_hang_tot_nghiep.databinding.FragmentMyBillBinding;
import com.example.app_ban_hang_tot_nghiep.model.Product;
import com.example.app_ban_hang_tot_nghiep.model.ResponeBill;
import com.example.app_ban_hang_tot_nghiep.viewmodel.BillWaitingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyBillFragment extends Fragment implements BillAdapter.onItemCategoryClick {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private FragmentMyBillBinding mBinding;
    private BillWaitingViewModel mViewModel;
    private BillAdapter mAdapter;
    SharedPreferences sharedPreferences;
    String token;
    public ResponeBill mBill = new ResponeBill();

    // TODO: Rename and change types and number of parameters
    public static MyBillFragment newInstance(String param1, String param2) {
        MyBillFragment fragment = new MyBillFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentMyBillBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(this).get(BillWaitingViewModel.class);
        setUpAdapter();
        sharedPreferences = requireContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("tokenID", "xxx");
        mViewModel.getListCartData(token);
        mViewModel.listBill.observe(getViewLifecycleOwner(), data -> {
            Log.d("TAG", "bill:  " + data.getDate());
            mBill = data;
            mBinding.recycleBill.getAdapter().notifyDataSetChanged();
        });
        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    @Override
    public void ItemClick(Product items) {

    }

    public void setUpAdapter() {
        mAdapter = new BillAdapter(mBill, requireContext(), this);
        mBinding.recycleBill.setAdapter(mAdapter);
    }
}