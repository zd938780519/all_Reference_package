package com.mchsdk.paysdk.mylibrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Stack;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by zhangdadi on 2017/4/17.
 */

public class BaseActivity extends RxAppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private AlertDialog mAlertDialog;

    private static Stack<Activity> activityStack = new Stack<Activity>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    /**
     * 显示隐藏状态栏，全屏不变，只在有全屏时有效
     *
     * @param enable
     */
    public void setStatusBarVisibility(boolean enable) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (enable) {
            lp.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
        } else {
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 设置是否全屏
     *
     * @param enable
     */
    public void setFullScreen(boolean enable) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (enable) {
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    public void ToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void ToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Hide alert dialog if any.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }


    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
//    protected void requestPermission(final String permission, String rationale, final int requestCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(BaseActivity.this,
//                                    new String[]{permission}, requestCode);
//                        }
//                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//        }
//    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }


    public void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


//    public boolean checkLogin(Context context) {
//        // Check login status
//        if (SharedPreferencesUtility.getUserID(context) <= 0) {
//            ToastShort(context, "请登录.");
//            Intent i = new Intent(context, LoginActivity.class);
////            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//            return false;
//        }
//        return true;
//
//    }


    /* 添加Activity到堆栈
    */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        Activity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
