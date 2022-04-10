package com.example.app_ban_hang_tot_nghiep.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_ban_hang_tot_nghiep.ApiService;

public class ForgotPasswordViewModel extends ViewModel {
    public ApiService mApiService;
    public MutableLiveData<Boolean> isRegisterSuccess = new MutableLiveData<>();

}
