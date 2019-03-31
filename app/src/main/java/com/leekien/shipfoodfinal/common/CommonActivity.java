package com.leekien.shipfoodfinal.common;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.leekien.shipfoodfinal.R;

import static android.support.constraint.Constraints.TAG;

public class CommonActivity extends Activity {
    public static boolean isNullOrEmpty(Object input) {
        if (input == null) {
            return true;
        }
        if (input instanceof String) {
            return input.toString().trim().isEmpty();
        }
        if (input instanceof EditText) {
            return ((EditText) input).getText().toString().trim().isEmpty();
        }
        if (input instanceof List) {
            return ((List) input).isEmpty();
        }

        if(input instanceof HashMap){
            return ((HashMap)input).isEmpty();
        }

        return input instanceof ArrayList && ((ArrayList) input).isEmpty();
    }
    public static Dialog createAlertDialog(Activity act, String message, String title) {
        try {
            return new MaterialDialog.Builder(act)
                    .title(title)
                    .content(message)
                    .positiveText("Đồng ý")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick( MaterialDialog dialog,  DialogAction which) {
                            if(dialog != null && dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    }).build();
        } catch (Exception e) {
            Log.d(TAG, "Error", e);
            return null;
        }
    }
}
