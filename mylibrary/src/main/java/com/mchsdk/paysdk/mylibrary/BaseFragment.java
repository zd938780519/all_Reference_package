package com.mchsdk.paysdk.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by zhangdadi on 2017/4/17.
 */
public class BaseFragment extends RxFragment {

    public void ToastShort(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void ToastLong(Context context, String message) {
        if (context == null) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public void jumpToActivity(Context context, Class activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);


    }
}
