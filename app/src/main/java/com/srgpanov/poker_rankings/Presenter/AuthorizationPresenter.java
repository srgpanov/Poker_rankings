package com.srgpanov.poker_rankings.Presenter;

import com.srgpanov.poker_rankings.View.AuthorizationInterface;

import io.reactivex.Observable;

/**
 * Created by srgpanov on 18.08.2017.
 */

public interface AuthorizationPresenter {
    void attachView(AuthorizationInterface view);
    void detachView();
    void viewIsReady();
    void Auth(String login, String password);
    void onForgotPassword();
}
