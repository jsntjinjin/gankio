package com.fastaoe.gankio.component.gank_meizi;

import android.os.Environment;
import android.util.ArrayMap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.model.database.DataBaseManager;
import com.fastaoe.gankio.model.database.MeiziImageProfile;
import com.fastaoe.gankio.model.models.RandomMeiziModel;
import com.fastaoe.gankio.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {

    private List<RandomData.ResultsBean> meiziResults;
    private RandomData.ResultsBean toShowMeizi;

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
        Observable.zip(DataModel.request(Token.RANDOM_MEIZI).execute(),
                ((RandomMeiziModel) DataModel.request(Token.RANDOM_MEIZI)).getMeiziSavingList(),
                (BiFunction<RandomData, List<MeiziImageProfile>, List<RandomData.ResultsBean>>) (randomData, meiziImageProfiles) -> {
                    List<RandomData.ResultsBean> temps = randomData.getResults();
                    for (RandomData.ResultsBean temp : temps) {
                        for (MeiziImageProfile meiziImageProfile : meiziImageProfiles) {
                            if (meiziImageProfile.getImageUrl().equals(temp.getUrl())) {
                                temp.setSaved(true);
                            }
                        }
                    }
                    return temps;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RandomData.ResultsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RandomData.ResultsBean> value) {
                        meiziResults.clear();
                        meiziResults.addAll(value);
                        int position = 0;
                        for (RandomData.ResultsBean resultsBean : value) {
                            if (resultsBean.isSaved()) {
                                position++;
                            }
                        }
                        getView().refreshContent(position);
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
    public void showMeiziDialog(int position) {
        toShowMeizi = getList().get(position);
        Observable.create((ObservableOnSubscribe<RandomData.ResultsBean>) e -> e.onNext(toShowMeizi))
                .map(resultsBean1 -> {
                    MeiziImageProfile meiziImageProfile = DataBaseManager.getInstance().getMeiziImageProfileDao()
                            .load(resultsBean1.get_id());
                    if (meiziImageProfile != null) {
                        resultsBean1.setSaved(true);
                    }
                    return resultsBean1;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultsBean12 -> getView().showMeizi(resultsBean12));
    }

    @Override
    public RandomData.ResultsBean getShowMeizi() {
        return toShowMeizi;
    }

    @Override
    public void saveMeizi(RandomData.ResultsBean resultsBean) {
        if (resultsBean.isSaved()) {
            getView().showToast("妹子已保存");
            return;
        }
        getView().saveMeiziStart();
        int length = getList().size();

        Observable.create((ObservableOnSubscribe<RandomData.ResultsBean>) e -> e.onNext(resultsBean))
                .map(resultsBean1 -> {
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(resultsBean1.getUrl());
                    objects.add(Glide.with(getView().getContext()).load(resultsBean1.getUrl())
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get());
                    objects.add(resultsBean1.get_id());
                    return objects;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(tempList -> {
                    File file = (File) tempList.get(1);
                    File pictureFolder = Environment.getExternalStorageDirectory().getAbsoluteFile();
                    File appDir = new File(pictureFolder, "fastaoe");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    long create_time = System.currentTimeMillis();
                    String fileName = create_time + ".jpg";
                    File destFile = new File(appDir, fileName);
                    copyFileUsingFileChannels(file, destFile);
                    return new String[]{(String) tempList.get(0), fileName, String.valueOf(create_time), (String) tempList.get(2)};
                })
                .map(objects -> {
                    MeiziImageProfile meiziImage =
                            new MeiziImageProfile(objects[3], objects[0], objects[1], Long.parseLong(objects[2]));
                    DataBaseManager.getInstance().getMeiziImageProfileDao().insert(meiziImage);
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        toShowMeizi.setSaved(true);
                        for (RandomData.ResultsBean bean : getList()) {
                            if (bean.get_id().equals(toShowMeizi.get_id())) {
                                bean.setSaved(true);
                                break;
                            }
                        }
                        getView().dialogRefresh(value);
                        int position = getView().setSavePosition();
                        getView().saveTextChanged((position != 9 ? "继续保存" : "已存储") + "（" + position + "/" + length + "）");
                        getView().saveMeiziEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("saveMeizi", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void saveMeizi(List<RandomData.ResultsBean> list) {
        getView().saveMeiziStart();
        int length = list.size();
        Observable.create((ObservableOnSubscribe<List<RandomData.ResultsBean>>) e -> e.onNext(list))
                .flatMap(Observable::fromIterable)
                .filter(resultsBean -> !resultsBean.isSaved())
                .map((Function<RandomData.ResultsBean, List<Object>>) resultsBean -> {
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(resultsBean.getUrl());
                    objects.add(Glide.with(getView().getContext()).load(resultsBean.getUrl())
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get());
                    objects.add(resultsBean.get_id());
                    return objects;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(tempList -> {
                    File file = (File) tempList.get(1);
                    File pictureFolder = Environment.getExternalStorageDirectory().getAbsoluteFile();
                    File appDir = new File(pictureFolder, "fastaoe");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    long create_time = System.currentTimeMillis();
                    String fileName = create_time + ".jpg";
                    File destFile = new File(appDir, fileName);
                    copyFileUsingFileChannels(file, destFile);
                    return new String[]{(String) tempList.get(0), fileName, String.valueOf(create_time), (String) tempList.get(2)};
                })
                .map(objects -> {
                    MeiziImageProfile meiziImage =
                            new MeiziImageProfile(objects[3], objects[0], objects[1], Long.parseLong(objects[2]));
                    DataBaseManager.getInstance().getMeiziImageProfileDao().insert(meiziImage);
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        for (RandomData.ResultsBean resultsBean : getList()) {
                            resultsBean.setSaved(true);
                        }
                        getView().saveTextChanged("已存储（" + getView().setSavePosition() + "/" + length + "）");
                        getView().saveMeiziEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("saveMeizi", e.toString());
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
