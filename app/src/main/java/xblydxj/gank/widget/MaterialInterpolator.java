package xblydxj.gank.widget;

import android.view.animation.Interpolator;

/**
 * Created by xblydxj
 * on 2016/8/12/026.
 */
public class MaterialInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        if(input<=0.5){
           return 4*input*input*input;
        }else{
            return 4*(input-1)*(input-1)*(input-1) + 1;
        }
//        if (input < (1 / 2.75))
//            return (7.5625f * input * input);
//        else if (input < (2 / 2.75))
//            return (7.5625f * (input -= (1.5f / 2.75f)) * input + 0.75f);
//        else if (input < (2.5 / 2.75))
//            return (7.5625f * (input -= (2.25f / 2.75f)) * input + 0.9375f);
//        else
//            return (7.5625f * (input -= (2.625f / 2.75f)) * input + 0.984375f);
    }
}