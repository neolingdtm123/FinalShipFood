package com.leekien.shipfoodfinal;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.leekien.shipfoodfinal.customView.RobEditText;

import java.text.DecimalFormat;

public class AppUtils {
    public static void hideSoftKeyboard(Activity activity, RobEditText editText) {
        if(activity==null)
        {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public static String formatMoney(String input) {
        if (input.trim().equals("")) {
            return "";
        }
        try {
            Double value = Double.parseDouble(input);
            DecimalFormat formatter = new DecimalFormat("#,###,###.####");
            return formatter.format(value)+" đ";
//            String[] split = formatter.format(value).split("\\.");
//            return split[0].replaceAll(",", ".") + (split.length > 1 ? "," + split[1] : "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String money = input +" đ";
        return money;
    }
    public static void showKeybord(Activity activity, RobEditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
