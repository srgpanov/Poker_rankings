package com.srgpanov.poker_rankings.Presenter;

/**
 * Created by srgpanov on 18.10.2017.
 */

public interface PresenterFactory<T extends Presenter> {
    T create();
}