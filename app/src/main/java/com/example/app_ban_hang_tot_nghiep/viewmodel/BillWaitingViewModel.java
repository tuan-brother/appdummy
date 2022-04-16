package com.example.app_ban_hang_tot_nghiep.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_ban_hang_tot_nghiep.ApiService;
import com.example.app_ban_hang_tot_nghiep.ApiUtils;
import com.example.app_ban_hang_tot_nghiep.model.Cart;
import com.example.app_ban_hang_tot_nghiep.model.ResponeBill;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillWaitingViewModel extends ViewModel {
    public ApiService mApiService;
    public MutableLiveData<ResponeBill> listBill = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteSuccess = new MutableLiveData<>();

    public void getListCartData(String token) {
        mApiService = ApiUtils.getApiService();

        mApiService.getBill(token).enqueue(new Callback<ResponeBill>() {
            @Override
            public void onResponse(Call<ResponeBill> call, Response<ResponeBill> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    listBill.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponeBill> call, Throwable t) {

            }
        });
    }
}
