package com.zyw.horrarndoo.yizhi.presenter.movie;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.douban.MovieDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.MovieDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.PersonBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.model.movie.MovieDetailModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.DoubanMoreDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class MovieDetailPresenter extends MovieDetailContract.MovieDetailPresenter {

    @NonNull
    public static MovieDetailPresenter newInstance() {
        return new MovieDetailPresenter();
    }

    @Override
    public void loadMovieDetail(String id) {
        if (mIView == null || mIModel == null)
            return;

        mRxManager.register(mIModel.getMovieDetail(id).subscribe(new Consumer<MovieDetailBean>() {
            @Override
            public void accept(MovieDetailBean bean) throws Exception {
                if (mIView == null)
                    return;

                mIView.showMovieDetail(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView == null)
                    return;
                mIView.showToast("Network error.");
                mIView.showNetworkError();
            }
        }));
    }

    @Override
    public void onItemClick(int position, PersonBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_TITLE, item.getName());
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_URL, item.getAlt());
        mIView.startNewActivity(DoubanMoreDetailActivity.class, bundle);
    }

    @Override
    public void onHeaderClick(SubjectsBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_TITLE, bean.getTitle());
        bundle.putString(BundleKeyConstant.ARG_KEY_DOUBAN_MORE_DETAIL_URL, bean.getAlt());
        mIView.startNewActivity(DoubanMoreDetailActivity.class, bundle);
    }

    @Override
    public MovieDetailModel getModel() {
        return MovieDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}