package com.example.app_ban_hang_tot_nghiep.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Utils {

    public String convertMoney(int money) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance(localeVN));

        return format.format(money);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isvalidCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }
}
