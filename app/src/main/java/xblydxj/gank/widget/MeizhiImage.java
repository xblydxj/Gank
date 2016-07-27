package xblydxj.gank.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 46321 on 2016/7/26/026.
 */
public class MeizhiImage extends ImageView {
    public MeizhiImage(Context context) {
        this(context, null);
    }

    public MeizhiImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeizhiImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
