package com.fastaoe.gankio.component.gank_meizi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.RandomData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {

    private List<RandomData.ResultsBean> meiziResults;

    @Override
    public List<RandomData.ResultsBean> getList() {
        if (meiziResults == null) {
            meiziResults = new ArrayList<>();
        }
        return meiziResults;
    }

    @Override
    public void refreshContent() {
        if (!isViewAttached()) {
            return;
        }

        //noinspection unchecked
        DataModel.request(Token.RANDOM_MEIZI)
                .execute()
                .subscribe(new Observer<RandomData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RandomData value) {
                        meiziResults.clear();
                        meiziResults.addAll(value.getResults());
                        getView().refreshContent();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    @Override
    public void saveMeizi(List<RandomData.ResultsBean> list) {
        int length = list.size();
        int position = 0;
        //        Observable.create((ObservableOnSubscribe<String>) e -> {
        //            for (RandomData.ResultsBean resultsBean : list) {
        //                e.onNext(resultsBean.getUrl());
        //            }
        //        })
        //                .subscribeOn(AndroidSchedulers.mainThread())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .map(new Function<String, Integer>() {
        //                    @Override
        //                    public Integer apply(String url) throws Exception {
        //                        if (saveImage(url, getView().getContext())) {
        //                            return 1;
        //                        }
        //                        return 0;
        //                    }
        //                })
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(s -> {
        //                    position = s + position;
        //                    getView().showToast("已存储（" + position + "/" + length + "）");
        //                });
        for (RandomData.ResultsBean resultsBean : list) {
            if (saveImage(resultsBean.getUrl(), getView().getContext())) {
                position++;
            }
            getView().saveTextChanged("已存储（" + position + "/" + length + "）");
        }
    }

    private boolean saveImage(String url, Context context) {
        try {
            FutureTarget<File> fileFutureTarget = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            File file = fileFutureTarget.get();
            File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
            File appDir = new File(pictureFolder, "fastaoe");
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File destFile = new File(appDir, fileName);
            copyFileUsingFileChannels(file, destFile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
