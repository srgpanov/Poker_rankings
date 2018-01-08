package com.srgpanov.poker_rankings.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.srgpanov.poker_rankings.Presenter.Presenter;
import com.srgpanov.poker_rankings.Presenter.PresenterFactory;
import com.srgpanov.poker_rankings.Presenter.PresenterLoader;

/**
 * Created by srgpanov on 18.10.2017.
 */

public abstract class BaseActivity<P extends Presenter<V>, V> extends AppCompatActivity {
    private static final int LOADER_ID = 1000;
    protected P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loader<P> loader = getSupportLoaderManager().getLoader(loaderId());
        if (loader == null) {
            initLoader();
        } else {
            this.presenter = ((PresenterLoader<P>) loader).getPresenter();
            onPresenterCreatedOrRestored(presenter);
        }

    }

    private void initLoader() {
        getSupportLoaderManager().initLoader(loaderId(), null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {

                return new PresenterLoader<>(BaseActivity.this, getPresenterFactory());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                BaseActivity.this.presenter = presenter;
                onPresenterCreatedOrRestored(presenter);
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                BaseActivity.this.presenter = null;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttached(getPresenterView());
    }

    @Override
    protected void onStop() {
        presenter.onViewDetached();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @NonNull
    protected V getPresenterView() {
        return (V) this;
    }

    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();
    protected abstract void onPresenterCreatedOrRestored(@NonNull P presenter);

    protected int loaderId() {
        return LOADER_ID;
    }
}
