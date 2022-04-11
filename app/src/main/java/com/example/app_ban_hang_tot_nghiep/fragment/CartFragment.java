package com.example.app_ban_hang_tot_nghiep.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.adapter.CartAdapter;
import com.example.app_ban_hang_tot_nghiep.databinding.FragmentCartBinding;
import com.example.app_ban_hang_tot_nghiep.model.ItemProductCartItem;
import com.example.app_ban_hang_tot_nghiep.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements CartAdapter.onItemClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private FragmentCartBinding mBinding;
    private MainViewModel mViewModel;
    private CartAdapter mAdapter;
    public List<ItemProductCartItem> mListData = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCartBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setUpAdapter();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenID", "xxx");
        Log.d("TAG567", "onCreateView: " + token);
        mViewModel.getListCartData(token);
        mViewModel.listCart.observe(getViewLifecycleOwner(), cart -> {
            if (cart.getProducts().size() > 0) {
                mListData.clear();
                mListData.addAll(cart.getProducts());
                mBinding.recycleCart.getAdapter().notifyDataSetChanged();
            }
        });
        mBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        Log.d("TAG765", "onCreateView: " + mViewModel.listSearch.size());
        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    @Override
    public void ItemClick(ItemProductCartItem items) {

    }

    public void setUpAdapter() {
        mAdapter = new CartAdapter(mListData, getContext(), this);
        mBinding.recycleCart.setAdapter(mAdapter);
    }
}