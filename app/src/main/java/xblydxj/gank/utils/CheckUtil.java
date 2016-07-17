package xblydxj.gank.utils;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public class CheckUtil {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
