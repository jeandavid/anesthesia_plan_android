package com.elishou.anesthesiaplan.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Ref: https://github.com/googlesamples/android-architecture/tree/dev-todo-mvvm-live
 *
 * Provides a method to show a Snackbar.
 */
public class SnackbarUtils {

    public static void showSnackbar(View v, String snackbarText) {
        if (v == null || snackbarText == null) {
            return;
        }
        Snackbar.make(v, snackbarText, Snackbar.LENGTH_LONG).show();
    }
}

