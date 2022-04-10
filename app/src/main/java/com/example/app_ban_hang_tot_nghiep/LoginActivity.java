package com.example.app_ban_hang_tot_nghiep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.app_ban_hang_tot_nghiep.databinding.ActivityLoginBinding;
import com.example.app_ban_hang_tot_nghiep.fragment.LoginFragment;
import com.example.app_ban_hang_tot_nghiep.fragment.StartFragment;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        gotoLoginFragment();
    }


    public void gotoLoginFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLogin, new LoginFragment()).commit();
    }
}