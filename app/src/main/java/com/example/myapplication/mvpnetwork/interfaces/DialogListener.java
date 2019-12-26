package com.example.myapplication.mvpnetwork.interfaces;

import android.app.AlertDialog;
import android.widget.TextView;

/**
 * Created by use on 2018/3/8.
 */

public interface DialogListener {
    void dialogCallBack(final AlertDialog dialog, TextView left, TextView right);
}
