package com.srgpanov.poker_rankings.Presenter;


/**
 * Created by srgpanov on 18.10.2017.
 */

public interface Presenter<V> {
    void onViewAttached(V view);
    void onViewDetached();
    void onViewDestroyed();
    void onDestroyed();
}
