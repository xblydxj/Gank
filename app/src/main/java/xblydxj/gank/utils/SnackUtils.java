package xblydxj.gank.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class SnackUtils {
    public static void showSnackLong(View view,String show,String actionShow) {
        final Snackbar snackbar = Snackbar.make(view, show, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionShow, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
