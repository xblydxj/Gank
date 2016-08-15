package xblydxj.gank.modules.picture;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.utils.ShareUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj.
 * on 2016/8/10/010
 */
public class BigPicturePresenter implements BigPictureContract.Presenter {
    private String url;
    private BigPictureContract.View mView;
    private CompositeSubscription mSubscription;

    public BigPicturePresenter(String url, BigPictureContract.View baseView) {
        mView = checkNotNull(baseView);
        mView.setPresenter(this);
        this.url = url;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }


    @Override
    public Uri savePicture(final Bitmap bitmap) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            mView.showSnack("找不到SD卡~");
            return null;
        }
        final File dir = new File(Environment.getExternalStorageDirectory(), "fuckCar/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd-HH-mm-ss", Locale.getDefault());
        final String fileName = simpleDate.format(now.getTime())+".jpg";
        final File mPath = new File(dir,fileName);
        mView.showDialog();
        Observable.just(bitmap)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        mView.showSnack("保存成功~ " + dir + fileName + ".jpg");
                        mView.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSnack("发生了未知的错误 ,保存失败~ ");
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        FileOutputStream out;
                        try {
                            out = new FileOutputStream(mPath);
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return Uri.fromFile(mPath);
    }

    @Override
    public void showShare(FragmentActivity context, Bitmap bitmap) {
        ShareUtil.shareImage(context,savePicture(bitmap),"美屡~~");
//        ShareSDK.initSDK(context);
//        OnekeyShare oks = new OnekeyShare();
//        oks.disableSSOWhenAuthorize();
//        oks.setTitle("gank.io每日分享");
//        oks.setImageUrl(url);
//        oks.setComment("gank.io每日分享");
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                mView.showSnack("分享成功~");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                mView.showSnack("发生了一些问题，分享失败了~");
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                mView.showSnack("分享失败~");
//            }
//        });
//        oks.show(context);
    }
}
