package com.srgpanov.poker_rankings.Presenter;

import com.srgpanov.poker_rankings.View.MainActivityInterface;

/**
 * Created by srgpanov on 18.10.2017.
 */

public class MainActivityPresenterFactory implements PresenterFactory<MainActivityPresenter> {
    @Override
    public MainActivityPresenter create() {
        return new MainActivityPresenter();
    }
}
