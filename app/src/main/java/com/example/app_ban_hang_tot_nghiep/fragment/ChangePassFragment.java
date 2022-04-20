package com.example.app_ban_hang_tot_nghiep.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang_tot_nghiep.R;
import com.example.app_ban_hang_tot_nghiep.databinding.FragmentChangePassBinding;


public class ChangePassFragment extends Fragment {

    private FragmentChangePassBinding mBinding;

    public ChangePassFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChangePassFragment newInstance(String param1, String param2) {
        ChangePassFragment fragment = new ChangePassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentChangePassBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return mBinding.getRoot();
    }
}