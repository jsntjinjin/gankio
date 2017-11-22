package com.fastaoe.gankio.component.gank_meizi;

import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        getView().saveMeiziStart();
        int length = list.size();
        Observable.create((ObservableOnSubscribe<List<RandomData.ResultsBean>>) e -> e.onNext(list))
                .flatMap(resultsBeans -> Observable.fromIterable(resultsBeans))
                .map(resultsBean -> Glide.with(getView().getContext()).load(resultsBean.getUrl()).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(fileFutureTarget -> fileFutureTarget.get())
                .map(file -> {
                    File pictureFolder = Environment.getExternalStorageDirectory().getAbsoluteFile();
                    File appDir = new File(pictureFolder, "fastaoe");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    copyFileUsingFileChannels(file, destFile);
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        getView().saveTextChanged("已存储（" + getView().setSavePosition() + "/" + length + "）");
                        getView().saveMeiziEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("saveMeizi", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
