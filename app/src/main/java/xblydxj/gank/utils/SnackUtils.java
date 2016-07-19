package xblydxj.gank.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class SnackUtils {

    private static Snackbar snackbar;

    public static void showSnackLong(View view, String show, String actionShow) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, show, Snackbar.LENGTH_LONG);
        }
        snackbar.setText(show);
        snackbar.setAction(actionShow, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public static void showSnackShort(View view, String show) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, show, Snackbar.LENGTH_SHORT);
        }
        snackbar.setText(show);
        snackbar.show();
    }
}
