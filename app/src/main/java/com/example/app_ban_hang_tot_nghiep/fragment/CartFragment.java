package com.example.app_ban_hang_tot_nghiep.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.adapter.CartAdapter;
import com.example.app_ban_hang_tot_nghiep.databinding.FragmentCartBinding;
import com.example.app_ban_hang_tot_nghiep.model.Cart;
import com.example.app_ban_hang_tot_nghiep.model.ItemCartMoreInfo;
import com.example.app_ban_hang_tot_nghiep.model.ItemProductCartItem;
import com.example.app_ban_hang_tot_nghiep.utils.Utils;
import com.example.app_ban_hang_tot_nghiep.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

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
    public List<ItemCartMoreInfo> mListData = new ArrayList<>();
    public Cart mCart;
    static CartFragment sCartFragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        sCartFragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        sCartFragment.setArguments(args);
        return sCartFragment;
    }

    public static CartFragment getInstance() {
        return sCartFragment;
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
        mBinding.spinKit.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenID", "xxx");
        Log.d("TAG567", "onCreateView: " + token);
        mViewModel.getListCartData(token);
        mViewModel.listCart.observe(getViewLifecycleOwner(), cart -> {
            mCart = cart;
            List<ItemCartMoreInfo> listItem = new ArrayList<>();
            Log.d("TAG444", "onCreateView: " + mViewModel.listSearch.size());
            mBinding.spinKit.setVisibility(View.GONE);
            if (mViewModel.listSearch.size() > 0) {

                for (int i = 0; i < cart.getProducts().size(); i++) {
                    for (int j = 0; j < mViewModel.listSearch.size(); j++) {
                        Log.d("TAG555", "onCreateView: " + cart.getProducts().get(i).getProductName() + "parent" + mViewModel.listSearch.get(j).getId().toString());
                        if (cart.getProducts().get(i).getProductId().equals(mViewModel.listSearch.get(j).getId())) {
                            ItemCartMoreInfo itemCart = new ItemCartMoreInfo();
                            itemCart.setAmount(cart.getProducts().get(i).getAmount());
                            itemCart.setPrice(cart.getProducts().get(i).getPrice());
                            itemCart.setImage(mViewModel.listSearch.get(j).getImage().get(0));
                            itemCart.setProductName(cart.getProducts().get(i).getProductName());
                            itemCart.setProductId(cart.getProducts().get(i).getProductId());
                            listItem.add(itemCart);
                        }
                    }
                }
                mBinding.tvTotalShow.setText(new Utils().convertMoney(cart.getTotal()));
                mListData.clear();
                mListData.addAll(listItem);
                mBinding.recycleCart.getAdapter().notifyDataSetChanged();
            }
        });
        onClick();

        Log.d("TAG765", "onCreateView: " + mViewModel.listSearch.size());
        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }

    @Override
    public void ItemClick(ItemCartMoreInfo items) {

    }

    public void setUpAdapter() {
        mAdapter = new CartAdapter(mListData, getContext(), this);
        mBinding.recycleCart.setAdapter(mAdapter);
    }

    public void onClick() {
        mBinding.tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mBinding.tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPay();
            }
        });
        mBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }

    public void goToPay() {
        FragmentManager fragmentManager = getParentFragmentManager();
        PayFragment fragment = new PayFragment().newInstance(mCart.getTotal(), new PayFragment.onBackSuccess() {
            @Override
            public void onSuccess() {
                PayFragment fragmentController = PayFragment.getInstance();
                requireActivity().getSupportFragmentManager().beginTransaction().remove(requireActivity().getSupportFragmentManager().findFragmentById(R.id.parent_content)).commit();
            }
        });
        fragmentManager.beginTransaction().add(R.id.parent_content, fragment, "pay").addToBackStack("toPay").commit();
    }
}